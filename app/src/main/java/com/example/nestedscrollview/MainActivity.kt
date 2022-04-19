
package com.example.nestedscrollview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nestedscrollview.ui.theme.NestedscrollviewTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NestedscrollviewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Nestedscrollview()
                }
            }
        }
    }
}
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun Nestedscrollview() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "TestApp",
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    ) {
        val scope = rememberCoroutineScope()
        val nestedScrollViewState = rememberNestedScrollViewState()
        VerticalNestedScrollView(state = nestedScrollViewState,
            header = {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colors.primary
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(text = "Make it easy")
                        Text(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas pulvinar quam quis diam tempus,")
                    }

                }
            },
            content = {
                val pagestate = rememberPagerState(pageCount = 10)
                val pages = (0..4).map { it }
                Column {
                    TabRow(selectedTabIndex = pagestate.currentPage,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.pagerTabIndicatorOffset(pagestate, tabPositions)
                            )
                        }
                    ) {
                        pages.forEachIndexed{
                            index, title ->
                            Tab(
                                text = { Text(text = "Tab ${title + 1}") },
                                selected = pagestate.currentPage == index,
                                onClick = {
                                    scope.launch {
                                        pagestate.animateScrollToPage(index)
                                    }
                                },
                            )
                        }

                    }
                    HorizontalPager(
                        modifier = Modifier.weight(1f),
                        state = pagestate
                    ) {
                        LazyColumn{
                            items(50){
                                ListItem {
                                    Card(modifier = Modifier.fillMaxWidth().padding(5.dp),
                                    shape = RoundedCornerShape(10.dp),
                                     elevation = 5.dp){
                                        Text(
                                            text = "Jetpack Compose ${it + 1}",
                                            modifier = Modifier.padding(15.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


            })
    }
}
