package bars

import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import org.w3c.dom.Document

object Html {

    private val indexHTML: HTML.() -> Unit = {
        head {
            link("/webjars/bootstrap/css/bootstrap.min.css", LinkRel.stylesheet)
            link("/assets/index.css", LinkRel.stylesheet)
            script(ScriptType.textJavaScript) {
                src = "/assets/index.js"
            }
        }
        body {
            nav("navbar fixed-top navbar-light bg-light") {
                a("/", classes = "navbar-brand") {
                    +"Bars"
                }
            }

            div("container-fluid") {
                ul {
                    id = "bars"
                }

                form("/bars") {
                    id = "form"
                    textInput {
                        id = "name"
                        required = true
                    }
                    submitInput { }
                }
            }
        }
    }

    val index: Document = createHTMLDocument().html(block = indexHTML)

}
