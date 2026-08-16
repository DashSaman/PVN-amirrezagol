package com.pvnetwork.core.branding

data class BrandIdentity(
    val productName: String,
    val shortName: String,
) {
    init {
        require(productName.isNotBlank()) { "product name must not be blank" }
        require(shortName.isNotBlank()) { "short name must not be blank" }
    }
}

/**
 * Product branding only. Application IDs, bundle IDs, publisher/legal entity
 * names and Store metadata are intentionally not invented at M0.
 */
object PVNetworkBrand {
    val identity: BrandIdentity = BrandIdentity(
        productName = "PVNetwork",
        shortName = "PVNetwork",
    )
}
