# Theme Node
Dynamically theme the ForgeRock out-of-the-box login UI on the fly.

An authentication node for ForgeRock's [Identity Platform][forgerock_platform] 6.5.2 and above. This node allows you to customize the look and feel of any authentication or registration flow and optionally change the default *Log In* button's text. A number of beautifully styled themes are included to bring color to your flows. You also have th option to fully customize an existing theme and create your own themes.


Download a release build fom the *release* tab or clone this repository to build your own release. Copy the .jar file from your download location or the *./target* directory (if you built it yourself) into the *../web-container/webapps/openam/WEB-INF/lib* directory where AM is deployed.  Restart the web container to pick up the new node.  The node will then appear in the authentication trees components palette.


## Related Nodes
- <a href="https://github.com/vscheuber/HTMLMessageNode">HTML Message Node</a>
- <a href="https://github.com/vscheuber/TermsAndConditionsNode">Terms And Conditions Node</a>
- <a href="https://github.com/vscheuber/ProgressiveProfileCompletion">Progressive Profile Completion Node</a>


## Usage
WIP

## Settings
WIP

## Building Authentication Nodes
The code in this repository has binary dependencies that live in the ForgeRock maven repository. Maven can be configured to authenticate to this repository by following the following [ForgeRock Knowledge Base Article](https://backstage.forgerock.com/knowledge/kb/article/a74096897).


The code described herein is provided on an "as is" basis, without warranty of any kind, to the fullest extent permitted by law. ForgeRock does not warrant or guarantee the individual success developers may have in implementing the sample code on their development platforms or in production configurations.

ForgeRock does not warrant, guarantee or make any representations regarding the use, results of use, accuracy, timeliness or completeness of any data or information relating to the sample code. ForgeRock disclaims all warranties, expressed or implied, and in particular, disclaims all warranties of merchantability, and warranties related to the code, or any service or software related thereto.

ForgeRock shall not be liable for any direct, indirect or consequential damages or costs of any type arising out of any action taken by you or others related to the sample code.

[forgerock_platform]: https://www.forgerock.com/platform/
