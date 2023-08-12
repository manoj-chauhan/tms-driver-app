package com.samrish.driver.ui.pages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.samrish.driver.models.Company
import com.samrish.driver.models.Trip
import com.samrish.driver.services.getCompanies
import com.samrish.driver.services.saveSelectedCompany
import com.samrish.driver.ui.components.CompanyList

@Composable
fun CompanySelection(
    navController: NavHostController,
    onCompanySelected: (assignment: Company) -> Unit
) {
    val context = LocalContext.current

    val companies = remember {
        mutableStateListOf<Company>()
    }

    getCompanies(
        context = context,
        onCompaniesFetched = {
            companies.clear()
            companies.addAll(it)
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Select Company")
        CompanyList(companyList = companies) {
            Log.i("Company Selection", "---> " + it.name)
            saveSelectedCompany(context, it.code, it.id)
            onCompanySelected(it)
        }
    }
}

