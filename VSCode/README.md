# kOS Syntax for VS Code
This plugin enables syntax highlighting for kOS scripts `.ks` in Visual Studio Code.

It leverages whatever VS Code theme you have enabled, so it should work with any theme you choose.

For a version of Visual Studio Code without Microsoft's telemetry, you can check out [VSCodium](https://vscodium.com/).


*****

### Installation

#### Using the Visual Studio Code Marketplace:

1. Open Visual Studio Code.
2. Go to the **Extensions** view by clicking on the Extensions icon in the Activity Bar on the side of the window or by pressing `Ctrl+Shift+X` (or `Cmd + Shift + X` on macOS).
3. Search for `kOS Syntax Highlighting | Kerbal Space Program`.
4. Click **Install**.

You can also find the extension on the [Visual Studio Marketplace](https://marketplace.visualstudio.com/items?itemName=Bradyns.kos-syntax)

#### Manual Installation:

1. Download the latest release from the [releases page]()
2. Open Visual Studio Code.
3. Go to the **Extensions** view by clicking on the Extensions icon in the Activity Bar on the side of the window or by pressing `Ctrl+Shift+X`(or `Cmd + Shift + X` on macOS).
4. Click on the `...` menu in the top right corner of the Extensions view and select **Install from VSIX...**.
5. Select the `.vsix` file you downloaded.
6. Click **Install**.

*****

### Previews:

Will be added soon.

*****

### Custom Colours

If you want to customise the colours for a specific kOS syntax scope, here's how you can do it:

1. **Open VS Code**.
2. **Find the Scope Name**:
   * Open the [`kos.tmLanguage.json`](./syntaxes/kos.tmLanguage.json) file.
   * Press `Ctrl + F` and search for the keyword you want to colour.
   * Copy the scope name (e.g., `support.function.kos`) directly above the keyword.
3. **Open the VS Code Settings JSON file**:
   * In VS Code, Press `Ctrl + Shift + P` (or `Cmd + Shift + P` on macOS) and type `Preferences: Open Settings (JSON)`.
4. **Add the following configuration** to your `settings.json` file:

    ```json
    {
      "editor.tokenColorCustomizations": {
        "textMateRules": [
          {
            "scope": "support.function.kos", // Replace with the scope you want to customise
            "settings": {
              "foreground": "#FF0000"  // Replace with your desired colour
            }
          }, //<-- Remove this comma if it's the last item in the list
        ]
      }
    }
    ```

    * 4.1 **Add More Than One**

        * You can add multiple customisations by adding more objects to the `textMateRules` array:

          ```json
          {
              "scope": "variable.parameter.kos", // Replace with the scope you want to customise
              "settings": {
              "foreground": "#FF0000"  // Replace with your desired colour
              }
          }, //<-- Remove this comma if it's the last item in the list
          ```

5. **Replace the `scope` and `foreground` values**:
   * Use the scope name you copied from the [`kos.tmLanguage.json`](./syntaxes/kos.tmLanguage.json) file.
   * Replace the `foreground` value with your desired colour.
        * **Note:** W3Schools has a [Colour Picker](https://www.w3schools.com/colors/colors_picker.asp) you can use.

6. **Save the `settings.json` file**.

*****

### How to Build

You will need to have Node.js installed to build this extension.

0. **Install Node.js**:

    If you have to install Node.js, you can download it from the [official website](https://nodejs.org/).

    - **NB:** *After installation, If you can't see the* `node` and `npm` *commands in your terminal, try restarting your computer to ensure they've been added to your PATH.*

#### Building the Extension

You can build the VSIX file for this extension using the `vsce` package. Here's how to do it:

1. **Install `vsce` Package**:

    ```bash
    npm install -g vsce
    ```

2. **Navigate to the VS Code Folder**:
    *It should be the folder that contains the `Package.json` folder.*

    * **Windows:**

        ```bash
        cd path\to\VSCode
        ```

    * **macOS/Linux:**

        ```bash
        cd path/to/VSCode
        ```

3. **Build the VSIX File**:

   ```bash
   vsce package
   ```

This should output a VSIX file in the root of the project folder, from which you can install the extension manually.
