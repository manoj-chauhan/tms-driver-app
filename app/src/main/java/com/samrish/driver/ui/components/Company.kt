package com.samrish.driver.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samrish.driver.models.Company
import com.samrish.driver.models.Trip

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

@Preview
@Composable
fun CompanyListItemPreview() {
    CompanyListItem(
        company = Company(1, "Apple Inc.", "APPL", "23 St. Peter's Marg, California, USA"),
        onClick = {}
    )
}