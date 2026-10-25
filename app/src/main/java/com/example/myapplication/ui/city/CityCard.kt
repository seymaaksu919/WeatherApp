
package com.example.myapplication.ui.city

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.City

@Composable
fun CityCard(
    city: City,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                Color(0xFFD6E8FF)
            } else {
                Color.White
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "🌤️",
                fontSize = 30.sp,
                modifier = Modifier
                    .background(
                        color = Color(0xFFE5F1FF),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(10.dp)
            )

            Spacer(modifier = Modifier.size(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = city.name,
                    fontSize = 18.sp,
                    color = Color(0xFF193B68)
                )

                Text(
                    text = "Ülke: ${city.country ?: "Belirtilmemiş"}",
                    fontSize = 14.sp,
                    color = Color(0xFF718096)
                )

                Text(
                    text = "Enlem: ${city.latitude}",
                    fontSize = 12.sp,
                    color = Color(0xFF718096)
                )

                Text(
                    text = "Boylam: ${city.longitude}",
                    fontSize = 12.sp,
                    color = Color(0xFF718096)
                )
            }

            Text(
                text = "›",
                fontSize = 30.sp,
                color = Color(0xFF3978C5)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun CityCardPreview() {
    CityCard(
        city = City(
            name = "İstanbul",
            country = "Türkiye",
            latitude = 41.01,
            longitude = 28.97
        ),
        isSelected = false,
        onClick = {}
    )
}
