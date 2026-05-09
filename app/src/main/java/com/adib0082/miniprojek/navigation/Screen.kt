package com.adib0082.miniprojek.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("mainScreen")
    data object FormBaru : Screen("detailScreen")
    data object FormUbah : Screen("detailScreen/{id}") {
        fun withId(id: Long): String = "detailScreen/$id"
    }
    data object RecycleBin : Screen("recycleBin")
}
