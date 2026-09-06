package bars.web

import androidx.compose.runtime.Composable
import dev.kilua.core.IComponent
import dev.kilua.html.style.globalStyle

@Composable
fun IComponent.appStyles() {
    globalStyle(selector = "*, *::before, *::after") {
        setStyle("box-sizing", "border-box")
    }
    globalStyle(selector = "html, body") {
        setStyle("margin", "0")
        setStyle("min-height", "100%")
        setStyle("font-family", "Inter, ui-sans-serif, system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif")
        setStyle("background", "#f1f5f9")
        setStyle("color", "#0f172a")
    }
    globalStyle(selector = "button, input") {
        setStyle("font", "inherit")
    }
    globalStyle(selector = ".topbar") {
        setStyle("position", "fixed")
        setStyle("inset", "0 0 auto 0")
        setStyle("z-index", "10")
        setStyle("border-bottom", "1px solid #e2e8f0")
        setStyle("background", "rgba(255, 255, 255, 0.95)")
        setStyle("box-shadow", "0 1px 2px rgba(15, 23, 42, 0.05)")
    }
    globalStyle(selector = ".topbar-content") {
        setStyle("display", "flex")
        setStyle("align-items", "center")
        setStyle("max-width", "48rem")
        setStyle("height", "4rem")
        setStyle("margin", "0 auto")
        setStyle("padding", "0 1.5rem")
    }
    globalStyle(selector = ".brand") {
        setStyle("color", "#0f172a")
        setStyle("font-size", "1.25rem")
        setStyle("font-weight", "700")
        setStyle("letter-spacing", "-0.025em")
        setStyle("text-decoration", "none")
    }
    globalStyle(selector = ".page") {
        setStyle("max-width", "48rem")
        setStyle("margin", "0 auto")
        setStyle("padding", "6rem 1.5rem 3rem")
    }
    globalStyle(selector = ".card") {
        setStyle("padding", "1.5rem")
        setStyle("border", "1px solid #e2e8f0")
        setStyle("border-radius", "0.75rem")
        setStyle("background", "#ffffff")
        setStyle("box-shadow", "0 1px 3px rgba(15, 23, 42, 0.08)")
    }
    globalStyle(selector = "h1") {
        setStyle("margin", "0")
        setStyle("font-size", "1.5rem")
        setStyle("line-height", "2rem")
        setStyle("letter-spacing", "-0.025em")
    }
    globalStyle(selector = ".subtitle") {
        setStyle("margin", "0.25rem 0 0")
        setStyle("color", "#64748b")
        setStyle("font-size", "0.875rem")
    }
    globalStyle(selector = ".status, .empty") {
        setStyle("margin", "1.5rem 0 0")
        setStyle("padding", "1rem")
        setStyle("border", "1px solid #e2e8f0")
        setStyle("border-radius", "0.5rem")
        setStyle("background", "#f8fafc")
        setStyle("color", "#64748b")
    }
    globalStyle(selector = ".bar-list") {
        setStyle("margin", "1.5rem 0 0")
        setStyle("padding", "0")
        setStyle("border", "1px solid #e2e8f0")
        setStyle("border-radius", "0.5rem")
        setStyle("overflow", "hidden")
        setStyle("list-style", "none")
    }
    globalStyle(selector = ".bar-list li") {
        setStyle("display", "flex")
        setStyle("justify-content", "space-between")
        setStyle("gap", "1rem")
        setStyle("padding", "0.875rem 1rem")
        setStyle("border-bottom", "1px solid #e2e8f0")
    }
    globalStyle(selector = ".bar-list li:last-child") {
        setStyle("border-bottom", "0")
    }
    globalStyle(selector = ".bar-id") {
        setStyle("color", "#94a3b8")
        setStyle("font-size", "0.75rem")
    }
    globalStyle(selector = ".error") {
        setStyle("margin-top", "1rem")
        setStyle("padding", "0.75rem 1rem")
        setStyle("border", "1px solid #fecaca")
        setStyle("border-radius", "0.5rem")
        setStyle("background", "#fef2f2")
        setStyle("color", "#b91c1c")
        setStyle("font-size", "0.875rem")
    }
    globalStyle(selector = ".bar-form") {
        setStyle("margin-top", "1.5rem")
    }
    globalStyle(selector = ".bar-form label") {
        setStyle("display", "block")
        setStyle("margin-bottom", "0.5rem")
        setStyle("font-size", "0.875rem")
        setStyle("font-weight", "600")
    }
    globalStyle(selector = ".form-row") {
        setStyle("display", "flex")
        setStyle("gap", "0.75rem")
    }
    globalStyle(selector = ".form-row input") {
        setStyle("min-width", "0")
        setStyle("flex", "1")
        setStyle("padding", "0.625rem 0.75rem")
        setStyle("border", "1px solid #cbd5e1")
        setStyle("border-radius", "0.5rem")
        setStyle("outline", "none")
    }
    globalStyle(selector = ".form-row input:focus") {
        setStyle("border-color", "#3b82f6")
        setStyle("box-shadow", "0 0 0 3px rgba(59, 130, 246, 0.2)")
    }
    globalStyle(selector = ".form-row button") {
        setStyle("padding", "0.625rem 1rem")
        setStyle("border", "0")
        setStyle("border-radius", "0.5rem")
        setStyle("background", "#2563eb")
        setStyle("color", "#ffffff")
        setStyle("font-weight", "600")
        setStyle("cursor", "pointer")
    }
    globalStyle(selector = ".form-row button:hover:not(:disabled)") {
        setStyle("background", "#1d4ed8")
    }
    globalStyle(selector = ".form-row button:disabled") {
        setStyle("cursor", "not-allowed")
        setStyle("opacity", "0.55")
    }
}
