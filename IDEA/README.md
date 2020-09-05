# Intellij IDEA - KerboScript(kOS) Plugin
This plugin enables support for kOS KerboScript in [IntelliJ IDEA](https://www.jetbrains.com/idea/) IDE.

Current kOS version supported: 1.1.2

### INSTALL
0. Install Intellij IDEA
1. Install [KerboScript(kOS)](https://www.jetbrains.com/idea/help/installing-updating-and-uninstalling-repository-plugins.html)

### COMPILE
0. Download IntelliJ and install the plugins recommended in the [prerequisites steps](http://www.jetbrains.org/intellij/sdk/docs/tutorials/custom_language_support/prerequisites.html) (no need to check out community source files or create a project)
1. Run `./gradlew buildPlugin` (or `gradlew.bat` on Windows) to build the plugin
2. Run the plugin

REFACTORING
-----------
### Inline (Ctrl+Alt+N)
**Variable usage**

    local b to a^2.
    local c to sqrt(|b).
    local d to b - 1.

will become

    local b to a^2.
    local c to sqrt((a^2)).
    local d to b - 1.

**Variable declaration**

    local |b to a^2.
    local c to sqrt(b).
    local d to b - 1.

will become

    local c to sqrt((a^2)).
    local d to (a^2) - 1.

**Simple function**

    function f {
        parameter x.
        parameter y.

        return x^2 + y.
    }

    local a to f(b, c).

will become

    local a to ((b)^2 + (c)).

### Differentiate (Ctrl+Alt+D)
**Variable**

    local y to x^2.

will become

    local y_ to 2*x_*x.

**Function**

    function f {
        parameter x.
        parameter y.

        return x^2 + y.
    }

will become

    function f_ {
        parameter x.
        parameter x_.
        parameter y_.

        return 2*x_*x + y_.
    }

> Please note that all derivative functions are created in separate file

### Simplify (Shift+Ctrl+S)

    local x to (a/b) + c + c + a*a + b/b.

will become

    local x to a/b + 2*c + a^2 + 1.

### Normalize (Shift+Ctrl+O)

    local x to (a/b) + c + c + a*a + b/b.

will become

    local x to (a + 2*c*b + a^2*b + b)/b.

