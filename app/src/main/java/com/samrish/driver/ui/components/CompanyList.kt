package com.samrish.driver.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Company
import com.samrish.driver.models.Trip
import com.samrish.driver.services.getCompanyDetail
import com.samrish.driver.services.getSelectedCompanyCode


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

@Composable
fun CompanyListItem(company: Company, onClick: (company: Company) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick(company)
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row() {
                Text(
                    text = company.name
                )
            }
        }
    }
}

@Composable
fun CompanyDetail() {
    val context = LocalContext.current

    var selectedCompany by remember {
        mutableStateOf<Company?>(null);
    }

    getSelectedCompanyCode(context)?.let {
        getCompanyDetail(
            context = context,
            companyCode = it,
            onCompanyDetailFetched = { it1 ->
                selectedCompany = it1
            }
        )
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {

            }
    ) {
        selectedCompany?.let {
            Column(modifier = Modifier.padding(16.dp)) {
                Row() {
                    Text(
                        text = it.code + " " + it.name
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun CompanyListItemPreview() {
    CompanyListItem(
        company = Company(1, "Apple Inc.", "APPL", "23 St. Peter's Marg, California, USA"),
        onClick = {}
    )
}

@Preview
@Composable
fun CompanyListPreview() {
    CompanyList(companyList = listOf(
        Company(1, "Apple Inc.", "APPL", "34 St. Peter Road, California, USA"),
        Company(2, "Microsoft Corp.", "MICR", "34 St. Peter Road, California, USA"),
    ), onCompanySelected = {})
}

@Preview
@Composable
fun CompanyDetailPreview() {
    CompanyDetail()
}
