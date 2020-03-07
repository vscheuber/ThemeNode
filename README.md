# Theme Node
Dynamically theme the ForgeRock out-of-the-box login UI on the fly.

An authentication node for ForgeRock's [Identity Platform][forgerock_platform] 6.5.2 and above. This node allows you to customize the look and feel of any authentication or registration flow and optionally change the default *Log In* button's text. A number of beautifully styled themes are included to bring color to your flows. You also have the option to fully customize an existing theme and create your own themes.


Download a release build fom the *release* tab or clone this repository to build your own release. Copy the .jar file from your download location or the *./target* directory (if you built it yourself) into the *../web-container/webapps/openam/WEB-INF/lib* directory where AM is deployed.  Restart the web container to pick up the new node.  The node will then appear in the authentication trees components palette.


## Related Nodes
- <a href="https://github.com/vscheuber/HTMLMessageNode">HTML Message Node</a>
- <a href="https://github.com/vscheuber/TermsAndConditionsNode">Terms And Conditions Node</a>
- <a href="https://github.com/vscheuber/ProgressiveProfileCompletion">Progressive Profile Completion Node</a>


## Usage
Use this node to esily theme your registration and login flows. Need a different set of colors, a different logo, a different footer, or a different button text? This will get you there in a few simple steps. A few professionally styled themes are selectable from a drop-down list but you can also knock yourself out and create your own custom theme using CSS or pointedly override specific aspects of a pre-configured or custom theme. Want the second factor screen in orange red? Easily done.

A sample flow could look like this:

![ScreenShot of a sample tree](img/tree.png)

<table>
  <tr>
  	<th>Page 1</th>
  	<th>Page 2</th>
  	<th>Page 3</th>
  	<th>Profile Page</th>
  </tr>
  <tr>
  	<th colspan="4">Fresh Orange</th>
  </tr>
  <tr>
  	<td><img alt="Fresh Orange - Page 1" src="img/fo_page1.png" width="250"/></td>
  	<td><img alt="Fresh Orange - Page 2" src="img/fo_page2.png" width="250"/></td>
  	<td><img alt="Fresh Orange - Page 3" src="img/fo_page3.png" width="250"/></td>
  	<td><img alt="Fresh Orange - Profile" src="img/fo_profile.png" width="250"/></td>
  </tr>
  <tr>
  	<th colspan="4">Soothing Blue</th>
  </tr>
  <tr>
  	<td><img alt="Soothing Blue - Page 1" src="img/sb_page1.png" width="250"/></td>
  	<td><img alt="Soothing Blue - Page 2" src="img/sb_page2.png" width="250"/></td>
  	<td><img alt="Soothing Blue - Page 3" src="img/sb_page3.png" width="250"/></td>
  	<td><img alt="Soothing Blue - Profile" src="img/sb_profile.png" width="250"/></td>
  </tr>
  <tr>
  	<th colspan="4">Harmonious Red</th>
  </tr>
  <tr>
  	<td><img alt="Harmonious Red - Page 1" src="img/hr_page1.png" width="250"/></td>
  	<td><img alt="Harmonious Red - Page 2" src="img/hr_page2.png" width="250"/></td>
  	<td><img alt="Harmonious Red - Page 3" src="img/hr_page3.png" width="250"/></td>
  	<td><img alt="Harmonious Red - Profile" src="img/hr_profile.png" width="250"/></td>
  </tr>
  <tr>
  	<th colspan="4">Trustworthy Green</th>
  </tr>
  <tr>
  	<td><img alt="Trustworthy Green - Page 1" src="img/tg_page1.png" width="250"/></td>
  	<td><img alt="Trustworthy Green - Page 2" src="img/tg_page2.png" width="250"/></td>
  	<td><img alt="Trustworthy Green - Page 3" src="img/tg_page3.png" width="250"/></td>
  	<td><img alt="Trustworthy Green - Profile" src="img/tg_profile.png" width="250"/></td>
  </tr>
  <tr>
  	<th colspan="4">Confident Blue Dark</th>
  </tr>
  <tr>
  	<td><img alt="Confident Blue Dark - Page 1" src="img/cbd_page1.png" width="250"/></td>
  	<td><img alt="Confident Blue Dark - Page 2" src="img/cbd_page2.png" width="250"/></td>
  	<td><img alt="Confident Blue Dark - Page 3" src="img/cbd_page3.png" width="250"/></td>
  	<td><img alt="Confident Blue Dark - Profile" src="img/cbd_profile.png" width="250"/></td>
  </tr>
  <tr>
  	<th colspan="4">Dependable Orange Dark</th>
  </tr>
  <tr>
  	<td><img alt="Dependable Orange Dark - Page 1" src="img/dod_page1.png" width="250"/></td>
  	<td><img alt="Dependable Orange Dark - Page 2" src="img/dod_page2.png" width="250"/></td>
  	<td><img alt="Dependable Orange Dark - Page 3" src="img/dod_page3.png" width="250"/></td>
  	<td><img alt="Dependable Orange Dark - Profile" src="img/dod_profile.png" width="250"/></td>
  </tr>
</table>


## Settings
<table cellspacing="10" width="100%">
  <tr>
  	<th align="left" colspan="2">Theme Node Settings</th>
  </tr>
  <tr valign="top">
  	<td width="20%">Theme Configuration</td>
  	<td>Select the theme configuration to use. The list contains both global and realm-specific configurations.<p>If the list is empty, go to <code>Configure > Global Services > Theme Service</code> and create global theme configurations or go to <code>Services > Add Service</code> and create realm theme configurations.<p>Global configurations are accessible from all realms, realm configurations only within the realm.</td>
  </tr>
  <tr valign="top">
  	<td valign="top">Overwrite Login Button</td>
  	<td>Enable this option to change the text of the <code>LOG IN</code> button.</td>
  </tr>
  <tr>
  	<td valign="top">Button Text</td>
  	<td>Specify localized texts to overwrite the <code>LOG IN</code> button with.</td>
  </tr>
  <tr>
  	<th align="left" colspan="2"/>
  </tr>
  <tr>
  	<th align="left" colspan="2">Theme Service Settings</th>
  </tr>
  <tr>
  	<th align="left" colspan="2"><code>Global Attributes</code></th>
  </tr>
  <tr>
  	<td valign="top">Enable</td>
  	<td>Enable this service to apply theme configuration to your registration and authentication trees. Disable to render the installed theme. Use this setting as an on/off switch for all Theme Nodes configured to use Theme Configurations from this service.</td>
  </tr>
  <tr>
  	<th align="left" colspan="2"><code>Secondary Configurations</code></th>
  </tr>
  <tr>
  	<td valign="top">Theme</td>
  	<td>Select a theme. Choose <code>None</code> to leave the currently active theme. Select <code>Custom</code> to create your own theme using CSS by configuring the values for Custom Theme and Custom Theme Declarations.</td>
  </tr>
  <tr>
  	<td valign="top">Replace Logo</td>
  	<td>Select this option to replace the logo.</td>
  </tr>
  <tr>
  	<td valign="top">Logo Properties</td>
  	<td>Specify any number of properties defining the new logo's <code>&lt;img></code> tag. At a minimum you must supply a <code>src</code> key and a value pointing to your new logo. If you do not provide a <code>src</code> key entry, the existing logo is removed but not replaced. You can reference transient/shared state variables anywhere in your values using the <code>{{variable}}</code> notation.<p>Sample ForgeRock logos to use:<li>Color: <a href="https://www.forgerock.com/img/ForgeRock_Vert_Color_Logo_RGB_R_med.svg">https://www.forgerock.com/img/ForgeRock_Vert_Color_Logo_RGB_R_med.svg</a><li>White: <a href="https://www.forgerock.com/img/ForgeRock_Vert_WHT_Logo_RGB_R_med.svg">https://www.forgerock.com/img/ForgeRock_Vert_WHT_Logo_RGB_R_med.svg</a></td>
  </tr>
  <tr>
  	<td valign="top">Overwrite Footer</td>
  	<td>Enable this option to change the footer text.</td>
  </tr>
  <tr>
  	<td valign="top">Footer Text</td>
  	<td>Specify localized texts to overwrite the footer button with. You can reference transient/shared state variables anywhere in your text using the <code>{{variable}}</code> notation.</td>
  </tr>
  <tr>
  	<td valign="top">Custom Theme</td>
  	<td>Specify the name of the custom theme to use. For this setting to take effect you must:<p>1) select <code>Custom</code> from the <code>Theme Configuration</code> drop-down list.<br>2) Create a theme definition by adding a unique name as the key and valid CSS declarations as the value in the <code>Custom Theme Definitions</code> list.<br>3) Identify the custom theme you want to use by name in this field.</td>
  </tr>
  <tr>
  	<td valign="top">Custom Theme Definitions</td>
  	<td>Specify any number of themes. The key holds a <code>unique name</code> and the value a valid block of <code>CSS</code> declarations making up the theme. You can reference transient/shared state variables anywhere in your style declaration using the <code>{{variable}}</code> notation.</td>
  </tr>
  <tr>
  	<td valign="top">Customize Theme</td>
  	<td>Enable this option to customize the theme.</td>
  </tr>
  <tr>
  	<td valign="top">Theme Customizations</td>
  	<td>Specify any number of properties. The key holds a <code>CSS Selector</code> expression and the value an inline <code>style</code> declaration. You can reference transient/shared state variables anywhere in your style declaration using the <code>{{variable}}</code> notation.</td>
  </tr>
</table>


<table width="100%">
  <tr>
  	<th align="left">Node Settings</th>
  </tr>
  <tr>
  	<td valign="top"><img alt="Node Config" src="img/node_config.png" width="250"/></td>
  </tr>
</table>


<table width="100%">
  <tr>
  	<th align="left" colspan="2">Service Settings</th>
  </tr>
  <tr>
  	<td valign="top" rowspan="2"><img alt="Service Config 1" src="img/service_config1.png" width="500"/></td>
  	<td valign="top"><img alt="Service Config 2" src="img/service_config2.png" width="500"/></td>
  </tr>
  <tr>
  	<td valign="top"><img alt="Service Config 3" src="img/service_config3.png" width="500"/></td>
  </tr>
</table>


## Building Authentication Nodes
The code in this repository has binary dependencies that live in the ForgeRock maven repository. Maven can be configured to authenticate to this repository by following the following [ForgeRock Knowledge Base Article](https://backstage.forgerock.com/knowledge/kb/article/a74096897).


The code described herein is provided on an "as is" basis, without warranty of any kind, to the fullest extent permitted by law. ForgeRock does not warrant or guarantee the individual success developers may have in implementing the sample code on their development platforms or in production configurations.

ForgeRock does not warrant, guarantee or make any representations regarding the use, results of use, accuracy, timeliness or completeness of any data or information relating to the sample code. ForgeRock disclaims all warranties, expressed or implied, and in particular, disclaims all warranties of merchantability, and warranties related to the code, or any service or software related thereto.

ForgeRock shall not be liable for any direct, indirect or consequential damages or costs of any type arising out of any action taken by you or others related to the sample code.

[forgerock_platform]: https://www.forgerock.com/platform/
