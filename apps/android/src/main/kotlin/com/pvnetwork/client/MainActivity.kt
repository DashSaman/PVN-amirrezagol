package com.pvnetwork.client

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pvnetwork.core.connection.ConnectionState
import com.pvnetwork.core.i18n.TextDirection
import com.pvnetwork.core.i18n.PVLocales
import com.pvnetwork.core.profile.PVProfile
import com.pvnetwork.engine.xray.VlessShareLinkImporter

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ClientState.ensure(this)
        setContent {
            PVNetworkApp(
                onConnect = { profile -> requestVpnAndConnect(profile) },
                onDisconnect = { PvVpnService.stop(this) },
                importLink = { link -> importLink(link) },
                deleteProfile = { id ->
                    ClientState.profiles.remove(com.pvnetwork.core.profile.ProfileId(id))
                    refresh()
                },
            )
        }
    }

    private fun requestVpnAndConnect(profileId: String) {
        val prepare = VpnService.prepare(this)
        if (prepare != null) {
            pendingProfileId = profileId
            runCatching { startActivityForResult(prepare, VPN_PERMISSION_REQUEST) }
        } else {
            PvVpnService.start(this, profileId)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == VPN_PERMISSION_REQUEST) {
            val pending = pendingProfileId
            pendingProfileId = null
            if (resultCode == Activity.RESULT_OK && pending != null) {
                PvVpnService.start(this, pending)
            }
        }
    }

    private fun importLink(link: String): String? = try {
        val store = ClientState.profiles
        val id = store.nextId()
        val imported = VlessShareLinkImporter(ClientState.secrets).import(link.trim(), id)
        store.add(imported.canonicalProfile)
        refresh()
        null
    } catch (failure: Throwable) {
        failure.message ?: "import failed"
    }

    private fun refresh() {
        ClientState.profileList = ClientState.profiles.all()
    }

    companion object {
        private const val VPN_PERMISSION_REQUEST = 100
        private var pendingProfileId: String? = null
    }
}

/** Process-wide singletons owned by the activity process. */
object ClientState {
    lateinit var secrets: AndroidSecretStore
        internal set
    lateinit var profiles: AndroidProfileStore
        internal set
    var profileList: List<PVProfile> = emptyList()
    var selectedProfileId: String? = null

    fun ensure(context: android.content.Context) {
        if (!::secrets.isInitialized) {
            secrets = AndroidSecretStore(context)
            profiles = AndroidProfileStore(context)
            profileList = profiles.all()
        }
    }
}

private enum class AppLanguage(val locale: com.pvnetwork.core.i18n.SupportedLocale) {
    ENGLISH(PVLocales.ENGLISH),
    PERSIAN(PVLocales.PERSIAN),
}

private data class Copy(
    val title: String,
    val profiles: String,
    val noProfiles: String,
    val pasteLink: String,
    val add: String,
    val connect: String,
    val disconnect: String,
    val stateDisconnected: String,
    val statePreparing: String,
    val stateConnecting: String,
    val stateConnected: String,
    val stateDisconnecting: String,
    val stateError: String,
    val delete: String,
    val language: String,
    val errorPrefix: String,
)

private fun copyFor(language: AppLanguage): Copy = when (language) {
    AppLanguage.ENGLISH -> Copy(
        title = "PVNetwork",
        profiles = "Profiles",
        noProfiles = "No profiles yet — paste a vless:// link",
        pasteLink = "vless://…",
        add = "Add",
        connect = "Connect",
        disconnect = "Disconnect",
        stateDisconnected = "Disconnected",
        statePreparing = "Preparing…",
        stateConnecting = "Connecting…",
        stateConnected = "Connected",
        stateDisconnecting = "Disconnecting…",
        stateError = "Error",
        delete = "Delete",
        language = "فا",
        errorPrefix = "Failed",
    )

    AppLanguage.PERSIAN -> Copy(
        title = "پی‌وی‌نت‌ورک",
        profiles = "پروفایل‌ها",
        noProfiles = "هنوز پروفایلی نیست — لینک vless:// را بچسبانید",
        pasteLink = "vless://…",
        add = "افزودن",
        connect = "اتصال",
        disconnect = "قطع اتصال",
        stateDisconnected = "قطع است",
        statePreparing = "در حال آماده‌سازی…",
        stateConnecting = "در حال برقراری…",
        stateConnected = "متصل شد",
        stateDisconnecting = "در حال قطع…",
        stateError = "خطا",
        delete = "حذف",
        language = "EN",
        errorPrefix = "ناموفق",
    )
}

@Composable
fun PVNetworkApp(
    onConnect: (String) -> Unit,
    onDisconnect: () -> Unit,
    importLink: (String) -> String?,
    deleteProfile: (String) -> Unit,
) {
    var language by remember { mutableStateOf(AppLanguage.PERSIAN) }
    val copy = copyFor(language)
    val layoutDirection = if (language.locale.direction == TextDirection.RTL) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }
    val snapshot by VpnHub.state.collectAsState()
    val busy = snapshot.state !in setOf(ConnectionState.DISCONNECTED, ConnectionState.ERROR)

    MaterialTheme(colorScheme = darkColorScheme()) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(copy.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        OutlinedButton(onClick = {
                            language = if (language == AppLanguage.PERSIAN) AppLanguage.ENGLISH else AppLanguage.PERSIAN
                        }) { Text(copy.language) }
                    }

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Text(
                                localizedState(snapshot.state, copy),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = when (snapshot.state) {
                                    ConnectionState.CONNECTED -> MaterialTheme.colorScheme.primary
                                    ConnectionState.ERROR -> MaterialTheme.colorScheme.error
                                    else -> MaterialTheme.colorScheme.onSurface
                                },
                            )
                            snapshot.reasonCode?.let {
                                Text(it, fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                if (busy) {
                                    OutlinedButton(onClick = onDisconnect) { Text(copy.disconnect) }
                                    CircularProgressIndicator(modifier = Modifier.height(20.dp))
                                } else {
                                    Button(
                                        onClick = { ClientState.selectedProfileId?.let(onConnect) },
                                        enabled = ClientState.selectedProfileId != null,
                                    ) {
                                        Text(copy.connect, fontSize = 16.sp)
                                    }
                                }
                            }
                        }
                    }

                    var link by remember { mutableStateOf("") }
                    var error by remember { mutableStateOf<String?>(null) }
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(copy.profiles, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            OutlinedTextField(
                                value = link,
                                onValueChange = { link = it },
                                label = { Text(copy.pasteLink) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(
                                    enabled = link.isNotBlank(),
                                    onClick = {
                                        error = importLink(link)
                                        if (error == null) link = ""
                                    },
                                ) { Text(copy.add) }
                            }
                            error?.let { Text("${copy.errorPrefix}: $it", fontSize = 12.sp, color = MaterialTheme.colorScheme.error) }
                            Spacer(Modifier.height(4.dp))
                            if (ClientState.profileList.isEmpty()) {
                                Text(copy.noProfiles, fontSize = 13.sp)
                            } else {
                                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    items(ClientState.profileList, key = { it.id.value }) { profile ->
                                        ProfileCard(
                                            profile = profile,
                                            copy = copy,
                                            selected = ClientState.selectedProfileId == profile.id.value,
                                            busy = busy,
                                            onSelect = { ClientState.selectedProfileId = profile.id.value },
                                            onDelete = { deleteProfile(profile.id.value) },
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileCard(
    profile: PVProfile,
    copy: Copy,
    selected: Boolean,
    busy: Boolean,
    onSelect: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(enabled = !busy, onClick = onSelect),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    profile.displayName,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                )
                OutlinedButton(enabled = !busy, onClick = onDelete) { Text(copy.delete) }
            }
            Text(
                "${profile.protocolId} · ${profile.endpoint.host}:${profile.endpoint.port}",
                fontSize = 12.sp,
            )
        }
    }
}

private fun localizedState(state: ConnectionState, copy: Copy): String = when (state) {
    ConnectionState.DISCONNECTED -> copy.stateDisconnected
    ConnectionState.PREPARING -> copy.statePreparing
    ConnectionState.REQUESTING_PERMISSION -> copy.statePreparing
    ConnectionState.CONNECTING -> copy.stateConnecting
    ConnectionState.AUTHENTICATING -> copy.stateConnecting
    ConnectionState.ESTABLISHING_TUNNEL -> copy.stateConnecting
    ConnectionState.CONNECTED -> copy.stateConnected
    ConnectionState.RECONNECTING -> copy.stateConnecting
    ConnectionState.DISCONNECTING -> copy.stateDisconnecting
    ConnectionState.ERROR -> copy.stateError
}
