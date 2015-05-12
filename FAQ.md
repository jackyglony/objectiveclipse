This project hasn't been going long enough for questions to be asked frequently. However, in the hope of impending success, here are some questions which might be asked frequently enough to merit answering them in advance. _In any case, the goal of a Frequently Asked Questions list is to turn said questions into Infrequently Asked Questions Because You Should Look At The FAQ instead._



# General Questions #

## What version of Eclipse and CDT is needed? ##

ObjectivEClipse needs Eclipse 3.5 and CDT 6.0, both available as part of the Galileo release train. There's a pre-packaged build for [Eclipse CDT Cocoa](http://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/galileo/R/eclipse-cpp-galileo-macosx-cocoa.tar.gz) from the Eclipse download site. From there, you'll need to add the [ObjectivEClipse update site](http://objectiveclipse.googlecode.com/svn/update/site.xml) to Eclipse (by going into Help -> Install New Software menu).

## Will it run on an older version of Eclipse/CDT? ##

No. There were some key changes in CDT in the 3.5 lifecycle that ObjectivEClipse needs; in any case, the project is still very much early access. By the time ObjectivEClipse is done, it's quite likely that Eclipse 3.6 will be available, so 3.5 makes sense as the earliest version for support.

But it's an open code-base, and if you want to test out or submit fixes to make it work with earlier versions, please feel free to raise a [contribution](http://code.google.com/p/objectiveclipse/wiki/ContributionQuestionnaire) to do so.

## How do I install ObjectivEClipse? ##

Assuming you're running Mac OS X with Eclipse 3.5, you need to:

**Install CDT from the update site http://download.eclipse.org/tools/cdt/releases/galileo/ or from the built-in Galileo update site** Install ObjectivEClipse from the update site http://objectiveclipse.googlecode.com/svn/update/site.xml or by downloading a built update site archive from http://code.google.com/p/objectiveclipse/downloads/list

There are help instructions on how to install at [help.eclipse.org](http://help.eclipse.org/galileo/topic/org.eclipse.platform.doc.user/tasks/tasks-124.htm)

## Will this ever be part of the CDT? ##

The goal of objectiveclipse is to bring Objective-C programming out of Xcode and the Apple world. Clearly, being part of the CDT would increase the user base, which would be a good thing. ObjectivEClipse is licensed under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html) and contributors are asked to fill out a [Contribution Questionnaire](http://code.google.com/p/objectiveclipse/wiki/ContributionQuestionnaire) to confirm that contributions are licensed under the EPL.

However, if the project fizzles out then it is unlikely to become part of the CDT. More users, more contributors and a wider audience will help to demonstrate the long-term viability of this project, and the possibility of being folded into the CDT. Until that time, it's an EPL project hosted on Google Code.

## Does it work on non-Apple platforms? ##

Not at the current time. However, it is being designed with the hope that one day, it will be able to be used for Objective-C development outside the Apple world.

## Does it work on Apple platforms? ##

For some definition of 'work', yes. There are limitations and known issues, and until Milestone 1.0, this should be considered early/alpha releases. It is not a replacement for Xcode yet.

## What about other portable Apple platforms? ##

ObjectivEClipse is designed to edit Objective-C code. It doesn't really matter what SDK you're targeting. That said, you should consult your EULAs before cross-compiling.

## Do I still need Xcode? ##

You need to be able to compile against the headers and SDK platform of your choice. Some of these may be in the all-in-one download that you get in Xcode, whilst some may be accessible from /System/Library/Frameworks for your current platform.

Secondly, the Xcode download also contains other useful tools like Interface Builder, Instruments, Dashcode which are orthogonal to ObjectivEClipse.

## What about Interface Builder and XIB files? ##

You can configure an external editor inside Eclipse. There are no plans at present to provide a UI builder. It should still be possible to use Interface Builder to create XIB files, and then have them incorporated inside an Eclipse project.

## Why are you doing this? What's wrong with Xcode? ##

Fifteen years ago, Project Builder and Interface Builder were top-of-the-range tools. Xcode has a shared history (if not implementation) with Project Builder, but fundamentally, the progress of the IDE has not advanced perhaps as much as other IDEs in the same timescale. Much of Apple's innovation comes from the libraries (e.g. CoreData) combined with the standardisation (they're available on all Mac platforms). However, it's not necessary to use Xcode to build Objective-C applications.

Secondly, there's a lot of additional tools that can be used which Xcode isn't capable of interacting with. Xcode is limited to SVN and CVS as version control systems, right when distributed version control systems like Hg and Git are becoming the next de-facto standards for version control. There's also Mylyn.

## Mylyn? This works with Mylyn? ##

If you have the Mylyn integration feature "Mylyn Bridge: C/C++ Development" installed as well, then yes, you can use Mylyn with Cocoa. Since it uses the structural breakdown, you should be able to use it to highlight method implementations as well as classes. There's some further documentation and screenshots on [the Mylyn page](http://code.google.com/p/objectiveclipse/wiki/Mylyn).

# How do I ... #

Most of these require changing the build properties from C/C++ Build -> Settings -> ObjC Linker/ObjC Compiler.

## Add a new framework ##

  * Right-click on the project and go to the project's properties
  * C/C++ Build -> Settings -> ObjC Linker -> Frameworks
  * Click on the leftmost icon (with the green plus symbol) on the top right. Then type in the framework name.

## Compile against a different SDK ##

  * Right-click on the project and go to the project's properties
  * C/C++ Build -> Settings -> ObjC Compiler -> Miscellaneous
  * Add the option `-isysroot /Path/To/Other.sdk`

This will allow you to e.g. compile against the 10.4 SDK on a 10.5 system, provided that you have the system installed. You can also mount an old image (or `Previous Systems` if you've done an upgrade) as the base.

## Enable for iterator loops ##

  * Right-click on the project and go to the project's properties
  * C/C++ Build -> Settings -> ObjC Compiler -> Miscellaneous
  * Add the option `-std=c99`

## Compile for a different architecture ##

  * Right-click on the project and go to the project's properties
  * C/C++ Build -> Settings -> ObjC Compiler -> Miscellaneous
  * Add the option {{{-arch ppc}} or similar. Valid options are:
    * `ppc` - for G3/G4 systems
    * `ppc64` - for G5 systems
    * `i386` - for Intel (32-bit) systems
    * `x86_64` - for Intel (64-bit) systems

## Compile fat binaries for multiple architectures ##

You can't, at least, [not yet](http://code.google.com/p/objectiveclipse/issues/detail?id=31). You could build them individually, and then use [lipo](http://developer.apple.com/documentation/Darwin/Reference/ManPages/man1/lipo.1.html) to stitch them together, which is pretty much what gcc does under the covers for fat binaries anyway.

## Enable code folding ##

It's not enabled by default, but if you go to the preferences (Command+, or under the Eclipse application menu) and then go to C/C++ -> Editor -> Folding you can then click the 'Enable folding when opening a new editor' checkbox. Note that this will not change the behaviour of existing editors.

Note that the default (only?) folding is Default C folding; and the 'structures' by default includes everything, so you might want to uncheck 'structures' in an Objective-C world.

# Known Issues #

There are [known bugs](http://code.google.com/p/objectiveclipse/issues/list), and there are [unknown bugs](http://code.google.com/p/objectiveclipse/issues/entry). [Please report](http://code.google.com/p/objectiveclipse/issues/entry) unknown bugs. Here are some of the known ones, correct at the time of writing:

## Unresolved Inclusion warnings ##

You might notice a CDT warning suggesting that

```
#import <Foundation/Foundation.h>
```

is an 'unresolved inclusion'. Basically, what this means, is that CDT doesn't know where to find the header file. Apple's gcc does some funky stuff with [Framework directories](http://developer.apple.com/documentation/MacOSX/Conceptual/BPFrameworks/Concepts/FrameworkAnatomy.html) which means that the `Foundation/Foundation.h` file is actually found in `/System/Library/Frameworks/Foundation.framework/Headers/Foundation.h` location. CDT doesn't (yet) know about these special framework directories, and so throws up the warning.

This will be fixed at some point in the future; however, in the meantime, you can ignore the warning. Fortunately, when passing the data to Apple's gcc, it will find (and compile) the header correctly.

You can track this via [bug 21](http://code.google.com/p/objectiveclipse/issues/detail?id=21), although the real fix will have to be implemented in the upstream CDT and may have to be delivered as a patch.

The alternative would be to replace the import with the fully qualified name, like

```
#import </System/Library/Frameworks/Foundation.framework/Headers/Foundation.h>
```

which will work on both Apple and non-Apple gcc, at the expense of not being able to take advantage of the more terse form. In addition, it makes it more difficult to switch between _platforms_ like the 10.4 SDK and 10.5 SDK.

(The import will be needed to support hyperlink-navigation, but that depends on the indexer [bug 2](http://code.google.com/p/objectiveclipse/issues/detail?id=2) which is not yet implemented; so doesn't have a negative effect at this stage. The advice is to live with the warning for now.)

## Header files aren't recognised as Objective-C source files ##

(This issue was fixed in Milestone 0.2)

You may get compile-time errors when using Objective-C source constructs (e.g. @interface) because Eclipse isn't capable of determining the difference between a C header file and an Objective-C header file through extension alone.

Fortunately, it's possible to add a language mapping which says that all C header files (with .h extension) should be treated as Objective-C source. This is done automatically for new projects created with Milestone 0.2 and above. To migrate existing projects, right-click on the project, and then go to the C/C++ General Properties -> Language Mappings. Add a mapping from C header file -> Objective-C.