# Update, July 2011 #

Thanks to Google Code's support of Git, and the fact that the CDT project has moved over to Git as well, most of the problems relating to getting ObjectivEClipse support off the ground have been removed. As a result, this project will be brought back to life again for the Juno release.

# This project has closed down #

(This project briefly made a re-appearance as part of the [SNAP developer programme](http://alblue.bandlem.com/2010/11/objectiveclipse-briefly-reappears.htmlSony) - if anyone from Sony wants to reinvigorate this project or get it into Eclipse CDT please twitter or mail me at @alblue)

Unfortunately this project has to shut down, primarily through lack of contributor interest but also due to significant refactoring changes of the upstream CDT for the Eclipse Helios release. In order to bind in Objective-C support into the existing C parser, it was necessary to put in hooks in order to support both Objective-C messages and also the blocks protocol. There weren't easy hooks to make this the case in the 6.0 version of the CDT, and so a reasonable amount of code duplication occurred; which given the extensive set of refactorings that happened in the CDT 7.0 timeframe (Eclipse Helios) means that it's impractical to move forward on the current codebase.

Hopefully, Objective-C support will come to the CDT at some point in the future, but if it does, it will have to be as an upstream partner in order to integrate the Objective-C support at the key parser level. (This could also mean that block support would be available in standalone C applications, for example.)

Thanks for your interest and support. For more details, see [this blog post](http://alblue.bandlem.com/2010/06/objectiveclipse-project-closing-doors.html).

# Historic page for interest purposes #

The aim of this project is to create an Objective-C development environment based on the open-source Eclipse tooling and piggybacking on CDT. This project is not yet ready for public consumption; rather, it is a playground for those wishing to help create a powerful IDE for a powerful language.

Yes, there's Xcode, but only on Macs. And it's more Xcarp than Xcode, frankly, when compared to other IDEs out there. The only major part of it that is at all useful is Interface Builder; and in any case, that's a separate tool which produces separate files and so could easily co-exist; we're not talking about re-inventing the wheel here.

It should also exist on more than just Macs, too. [GNUStep](http://www.gnu.org/software/gnustep/) runs just fine on other PCs, and there's no reason why other systems couldn't be targetted using the Objective-C language. But the primary goal will no doubt be people developing for (and maybe on) the Mac or iPhone platforms.

All code must be [EPL](http://www.eclipse.org/legal/epl-v10.html) clean for potential inclusion into Eclipse.org in the future. (At least Google Code now allows EPL licensed projects for real.)

Developers can join the [objectiveclipse-dev](http://groups.google.com/group/objectiveclipse-dev) group, whilst if you're just interested in updates by mail (and you're not subscribed to the [project's feed](http://code.google.com/feeds/p/objectiveclipse/updates/basic)) you can join the low-volume [objectiveclipse-announce](http://groups.google.com/group/objectiveclipse-announce) mailing list.

ObjectivEClipse requires Eclipse 3.5 and CDT 6.0. You can use the update sites to add ObjectivEClipse but bear in mind that it's an early access release at this stage. There's a list of [Milestones](http://code.google.com/p/objectiveclipse/wiki/Milestones) that we're aiming for, and a [FAQ](http://code.google.com/p/objectiveclipse/wiki/FAQ) for how to use it, and we're always interested in [people getting involved](http://code.google.com/p/objectiveclipse/wiki/GettingInvolved). There's more on the [Wiki](http://code.google.com/p/objectiveclipse/w/list).

[ObjectivEClipse update site](http://objectiveclipse.googlecode.com/svn/update/site.xml) or [download archive](http://code.google.com/p/objectiveclipse/downloads/list)