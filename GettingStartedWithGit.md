# Introduction #

Google Code uses SVN by default for its version control. However, it's possible to [use Git with Google Code](http://code.google.com/p/support/wiki/ExportingToGit) if that's your preference, and then make it available via tools like [GitHub](http://github.com/alblue/objectiveclipse) for example.

This isn't about why you should use Git, or the advantages of it over others. There's (biased) opinions all over the net, including [Why Git is Better than X.com](http://whygitisbetterthanx.com/) and plenty more. Suffice to say that Git is a distributed version control system which means that you can take a local copy of the project, work remotely (like on a train, as I am now) and still be productive, and then finally be able to update back into the master SVN repositority.

## Initial Checkout ##

To get a copy of the SVN repository (and assuming you don't want to fork [GitHub](http://github.com/alblue/objectiveclipse), run this in a shell:

`git svn clone --username _user.name_ -s https://objectiveclipse.googlecode.com/svn`

That will then print many messages, creating a Git version for every SVN version that exists:

```
Initialized empty Git repository in /Users/alex/Projects/ObjectivEClipse/svn/.git/
r1 = 6f3d5cf11ef94f1d151ac80c3fafaa865c002d19 (trunk)
W: +empty_dir: trunk/org.eclipse.cdt.objc.core
r7 = bbc5c145c7fe7f6553a71a7714f3b41bb5d0f531 (trunk)
W: +empty_dir: trunk/org.eclipse.cdt.objc.core.tests
r8 = ee2326e2a8a6d60fa31a0b49279f53a511191f74 (trunk)
W: +empty_dir: trunk/org.eclipse.cdt.objc.ui
r9 = 8b6b7fdcd87189b8c35cea353ea02856f518f8a4 (trunk)
...
	A	org.eclipse.cdt.objc.core/src/org/eclipse/cdt/objc/core/internal/dom/parser/objc/ObjCBlockScope.java
	M	org.eclipse.cdt.objc.core.tests/src/org/eclipse/cdt/objc/core/tests/ParseTest.java
r142 = d6e7f5cf93bcd34837f25b60e9bb9122346c6785 (trunk)
Checked out HEAD:
  https://objectiveclipse.googlecode.com/svn/trunk r142
```

## Subsequent updates ##

Once you've made changes, you can then feed them back. If you're a committer, you can commit them straight into the repository-if not, you can create a patch with `git diff` and then attach that to an issue tracker item.

The only thing to be aware of with Git and SVN is that SVN isn't a distributed system, so doesn't have the concept of merging. This means that prior to uploading any changes, your local Git repository must be _rebased_ against the current SVN head. Once you've done that, you can perform an upload with _dcommit_:

```
git svn rebase
git svn dcommit
```

This will then take your Git repository and create separate (individual) SVN versions for each of the changes that you've made. During the rebase operation, or before, you can squash commits into a single one if that helps.

Please also see the GettingStarted page.