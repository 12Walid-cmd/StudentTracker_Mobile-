package ca.hccis.studenttracker.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ca.hccis.studenttracker.R

data class StudyCarouselItem(
    val imageRes: Int,
    val title: String
)

@Composable
fun StudyImageCarousel() {
    val items = listOf(
        StudyCarouselItem(R.drawable.study_1, "Stay focused"),
        StudyCarouselItem(R.drawable.study_2, "Build consistency"),
        StudyCarouselItem(R.drawable.study_3, "Reach your goals")
    )

    val pagerState = rememberPagerState(pageCount = { items.size })

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Study Motivation",
                style = MaterialTheme.typography.titleLarge
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { page ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = items[page].imageRes),
                        contentDescription = items[page].title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = items[page].title,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}