package com.samrish.driver.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Company
import com.samrish.driver.models.Trip


@Composable
fun CompanyList(companyList: List<Company>, onCompanySelected: (trip: Company) -> Unit) {
    Log.i("Assignments", "Just before display  $companyList")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        companyList.forEach {
                company -> CompanyListItem(company=company, onClick=onCompanySelected)
        }
    }
}

@Preview
@Composable
fun CompanyListPreview() {
    CompanyList(companyList = listOf(
        Company("Apple Inc.", "APPL", "34 St. Peter Road, California, USA"),
        Company("Microsoft Corp.", "MICR", "34 St. Peter Road, California, USA"),
    ), onCompanySelected = {})
}

