# Note #

This project is no longer under active development.

# Dependencies #

  * Eclipse 3.5 with CDT 6.0, such as [the EPP CDT package](http://www.eclipse.org/downloads/packages/eclipse-ide-cc-developers/galileor) or [the Eclipse SDK](http://download.eclipse.org/eclipse/downloads/drops/R-3.5-200906111540/index.php) and then use the install manager to install CDT

It does **not** work with Eclipse 3.6 or CDT 7.0, and there are no plans to make it so.

# Source projects #

  * Use the subversion explorer from http://objectiveclipse.googlecode.com/svn/trunk to check out the following projects:
    * org.eclipse.cdt.objc.core
    * org.eclipse.cdt.objc.core.tests
    * org.eclipse.cdt.objc.core.ui
    * org.eclipse.cdt.objc.core.ui.tests

(If anyone wants to contribute a PSF for the above, please let me know ...)

# Running ObjectivEClipse #

You have to do some mangling (at the moment) to get the build working, once running in a hosted Eclipse session. (Note that for Eclipse 3.5, you can now do Export -> Plugins and Fragments -> Install into Host so that you don't have to fire up a new session)

  * Create an Objective C project, say 'HelloWorld'
  * For frameworks other than Foundation
    * Go to C/C++ Build - Settings - ObjC Linker - Frameworks
    * Click on the green + button inside the Frameworks specification, and add, AppKit (for NSApplication and friends), and so forth
  * Create a Source file test.m, with:
```
#import <Foundation/Foundation.h>
int main(int argc, char** argv)
{
  NSLog(@"Hello World");
  return 0;
}
```
  * Click on the Build button (in the C/C++ perspective)
  * Click on the Run button, Run As -> Local C/C++ Application
    * Should see 'Hello World' running in the console!
# Notes #
  * The DOM parser uses a bunch of internal references to the CDT core package at the moment. These will show up as discouraged access restrictions. You can either configure the preferences to ignore those errors/warnings Unless Eclipse is running in Strict mode (which almost all aren't) then this isn't going to be a problem.
--
Any problems, reach out to the dev mailing list or ping [@AlBlue](http://twitter.com/alblue)