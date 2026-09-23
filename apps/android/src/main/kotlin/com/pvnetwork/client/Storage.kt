package com.pvnetwork.client

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.pvnetwork.core.profile.Endpoint
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.core.profile.ProfileId
import com.pvnetwork.core.profile.ProfileOrigin
import com.pvnetwork.core.profile.SecretRef
import com.pvnetwork.core.security.SecretPurpose
import com.pvnetwork.core.security.SecretStore
import com.pvnetwork.core.security.clearSecret
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.security.KeyStore
import java.util.UUID
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Android secret boundary: AES-256-GCM with a non-exportable Android
 * Keystore key; ciphertext only ever touches app-private storage.
 */
class AndroidSecretStore(context: Context) : SecretStore {

    private val dir = File(context.filesDir, "secrets").apply { mkdirs() }

    override fun put(purpose: SecretPurpose, secret: CharArray): SecretRef {
        val ref = SecretRef("secret://android/${UUID.randomUUID()}")
        val plain = String(secret).toByteArray(Charsets.UTF_8)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, key())
        val iv = cipher.iv
        val encrypted = cipher.doFinal(plain)
        File(dir, fileName(ref)).writeBytes(iv + encrypted)
        return ref
    }

    override fun <T> withSecret(ref: SecretRef, block: (CharArray) -> T): T? {
        val file = File(dir, fileName(ref))
        if (!file.exists()) return null
        val blob = file.readBytes()
        if (blob.size <= IV_LENGTH) return null
        return try {
            val cipher = Cipher.getInstance(TRANSFORMATION)
            cipher.init(Cipher.DECRYPT_MODE, key(), GCMParameterSpec(TAG_LENGTH, blob, 0, IV_LENGTH))
            val plain = cipher.doFinal(blob, IV_LENGTH, blob.size - IV_LENGTH)
            val chars = String(plain, Charsets.UTF_8).toCharArray()
            try {
                block(chars)
            } finally {
                chars.clearSecret()
            }
        } catch (_: Throwable) {
            null
        }
    }

    override fun delete(ref: SecretRef): Boolean = File(dir, fileName(ref)).delete()

    private fun fileName(ref: SecretRef): String =
        ref.value.replace(Regex("[^A-Za-z0-9._-]"), "_") + ".bin"

    private fun key(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEYSTORE).apply { load(null) }
        (keyStore.getKey(KEY_ALIAS, null) as? SecretKey)?.let { return it }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        generator.init(
            KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .setRandomizedEncryptionRequired(true)
                .build(),
        )
        return generator.generateKey()
    }

    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "pvnetwork_profile_secrets"
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val IV_LENGTH = 12
        private const val TAG_LENGTH = 128
    }
}

/**
 * Profile persistence in app-private storage using the platform JSON
 * parser. Secrets stay in the SecretStore; only opaque references persist.
 */
class AndroidProfileStore(context: Context) {

    private val file = File(context.filesDir, "profiles.json")
    private val lock = Any()

    fun all(): List<PVProfile> = synchronized(lock) {
        if (!file.exists()) return emptyList()
        runCatching {
            val array = JSONArray(file.readText())
            buildList {
                for (i in 0 until array.length()) add(decode(array.getJSONObject(i)))
            }
        }.getOrElse { emptyList() }
    }

    fun add(profile: PVProfile) = synchronized(lock) { write(all() + profile) }

    fun remove(id: ProfileId) = synchronized(lock) { write(all().filterNot { it.id == id }) }

    fun find(id: ProfileId): PVProfile? = all().firstOrNull { it.id == id }

    fun nextId(): ProfileId = ProfileId(UUID.randomUUID().toString())

    private fun write(profiles: List<PVProfile>) {
        val array = JSONArray()
        profiles.forEach { array.put(encode(it)) }
        file.writeText(array.toString())
    }

    private fun encode(profile: PVProfile): JSONObject {
        val json = JSONObject()
        json.put("id", profile.id.value)
        json.put("displayName", profile.displayName)
        json.put("protocolId", profile.protocolId)
        json.put("host", profile.endpoint.host)
        json.put("port", profile.endpoint.port)
        json.put("origin", profile.origin.name)
        val secrets = JSONObject()
        profile.secretRefs.forEach { (k, v) -> secrets.put(k, v.value) }
        json.put("secretRefs", secrets)
        val extensions = JSONObject()
        profile.extensions.forEach { (k, v) -> extensions.put(k, v) }
        json.put("extensions", extensions)
        return json
    }

    private fun decode(json: JSONObject): PVProfile {
        val secrets = LinkedHashMap<String, SecretRef>()
        val secretRefs = json.getJSONObject("secretRefs")
        for (key in secretRefs.keys()) secrets[key] = SecretRef(secretRefs.getString(key))
        val extensions = LinkedHashMap<String, String>()
        val ext = json.getJSONObject("extensions")
        for (key in ext.keys()) extensions[key] = ext.getString(key)
        return PVProfile(
            id = ProfileId(json.getString("id")),
            displayName = json.getString("displayName"),
            protocolId = json.getString("protocolId"),
            endpoint = Endpoint(json.getString("host"), json.getInt("port")),
            secretRefs = secrets,
            extensions = extensions,
            origin = ProfileOrigin.valueOf(json.getString("origin")),
        )
    }
}
