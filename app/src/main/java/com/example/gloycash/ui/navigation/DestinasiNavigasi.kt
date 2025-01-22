package com.example.gloycash.ui.navigation

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}

// Keuangan
object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home"
}

// Pendapatan
object DestinasiHomePendapatan : DestinasiNavigasi {
    override val route = "home_pendapatan"
    override val titleRes = "Home_Pendapatan"
}

object DestinasiInsertPendapatan : DestinasiNavigasi {
    override val route = "insert_pendapatan"
    override val titleRes = "Insert Pendapatan"
}

object DestinasiDetailPendapatan: DestinasiNavigasi {
    override val route = "detail_pendapatan"
    const val ID = "id"
    override val titleRes = "Detail Pendapatan"
    val routeWithArg = "$route/{$ID}"
}

object DestinasiUpdatePendapatan : DestinasiNavigasi {
    override val route = "update_pendapatan"
    const val ID = "id"
    override val titleRes = "Edit Pendapatan"
    val routeWithArg = "$route/{$ID}"
}

//Aset
object DestinasiHomeAset : DestinasiNavigasi {
    override val route = "home_aset"
    override val titleRes = "Home_Aset"
}

object DestinasiInsertAset : DestinasiNavigasi {
    override val route = "insert_aset"
    override val titleRes = "Insert Aset"
}

object DestinasiDetailAset: DestinasiNavigasi {
    override val route = "detail_aset"
    const val ID = "id"
    override val titleRes = "Detail Aset"
    val routeWithArg = "$route/{$ID}"
}

object DestinasiUpdateAset : DestinasiNavigasi {
    override val route = "update_aset"
    const val ID = "id"
    override val titleRes = "Edit Aset"
    val routeWithArg = "$route/{$ID}"
}

//Kategori
object DestinasiHomeKategori : DestinasiNavigasi {
    override val route = "home_kategori"
    override val titleRes = "Home_Kategori"
}

object DestinasiInsertKategori : DestinasiNavigasi {
    override val route = "insert_kategori"
    override val titleRes = "Insert Kategori"
}

object DestinasiDetailKategori: DestinasiNavigasi {
    override val route = "detail_kategori"
    const val ID = "id"
    override val titleRes = "Detail Kategori"
    val routeWithArg = "$route/{$ID}"
}

object DestinasiUpdateKategori : DestinasiNavigasi {
    override val route = "update_kategori"
    const val ID = "id"
    override val titleRes = "Edit Kategori"
    val routeWithArg = "$route/{$ID}"
}
