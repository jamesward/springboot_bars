package bars.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import bars.shared.Bar
import bars.shared.IBarService
import dev.kilua.Application
import dev.kilua.compose.root
import dev.kilua.core.IComponent
import dev.kilua.form.text.text
import dev.kilua.html.*
import dev.kilua.rpc.getService
import dev.kilua.startApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val barService = getService<IBarService>()

    private var bars by mutableStateOf(emptyList<Bar>())
    private var name by mutableStateOf("")
    private var loading by mutableStateOf(true)
    private var submitting by mutableStateOf(false)
    private var error by mutableStateOf<String?>(null)

    override fun start() {
        root("root") {
            barsView()
        }
        loadBars()
    }

    private fun loadBars() {
        scope.launch {
            loading = true
            error = null
            try {
                bars = barService.listBars()
            } catch (exception: Exception) {
                error = exception.message ?: "Could not load bars"
            } finally {
                loading = false
            }
        }
    }

    private fun createBar() {
        if (submitting) return

        val submittedName = name.trim()
        if (submittedName.isEmpty()) {
            error = "Bar name must not be blank"
            return
        }

        scope.launch {
            submitting = true
            error = null
            try {
                val created = barService.createBar(submittedName)
                bars = bars + created
                name = ""
            } catch (exception: Exception) {
                error = exception.message ?: "Could not create bar"
            } finally {
                submitting = false
            }
        }
    }

    @Composable
    private fun IComponent.barsView() {
        appStyles()

        header(className = "topbar") {
            div(className = "topbar-content") {
                a(href = "/", className = "brand") {
                    +"Bars"
                }
            }
        }

        main(className = "page") {
            section(className = "card") {
                h1 {
                    +"Bars"
                }
                p(className = "subtitle") {
                    +"A Kotlin Multiplatform app powered by Kilua RPC."
                }

                when {
                    loading -> p(className = "status") { +"Loading bars…" }
                    bars.isEmpty() -> p(className = "empty") { +"No bars yet. Add the first one below." }
                    else -> ul(className = "bar-list") {
                        bars.forEach { bar ->
                            key(bar.id) {
                                li {
                                    span { +bar.name }
                                    span(className = "bar-id") { +"#${bar.id}" }
                                }
                            }
                        }
                    }
                }

                val errorMessage = error
                if (errorMessage != null) {
                    div(className = "error") {
                        role("alert")
                        +errorMessage
                    }
                }

                tag("form") {
                    className("bar-form")
                    onEvent<web.events.Event>("submit") { event ->
                        event.preventDefault()
                        createBar()
                    }

                    label(htmlFor = "bar-name") {
                        +"Bar name"
                    }
                    div(className = "form-row") {
                        text(
                            value = name,
                            id = "bar-name",
                            placeholder = "The Kotlin Arms",
                            required = true,
                            disabled = if (submitting) true else null,
                        ) {
                            onInput {
                                this@App.name = this.value ?: ""
                            }
                        }
                        button(if (submitting) "Adding…" else "Add bar") {
                            type(ButtonType.Submit)
                            disabled(submitting || name.isBlank())
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(::App)
}
