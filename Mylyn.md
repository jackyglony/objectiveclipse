# Mylyn #

Mylyn is a task focussed user interface, which helps reduce clutter when working with multiple files. (It's also a hook into an issue tracking system.)

CDT already has Mylyn support; you need to ensure that you select the "Mylyn Bridge: C/C++ Development" which is available as part of the Galileo update repository (should be present if you do Help -> Install New Software from Eclipse 3.5). Installing the Mylyn Bridge for C/C++ will require that you install the Mylyn base feature as well and the task focussed UI, but the update manager should be able to do that for you.

Once installed, you can create tasks from the Task Repositories view or Task List view. Tasks don't have to be associated with a bug tracking system, but they can be. Once the task has been created, it can be activated by clicking on the blue icon at the top left, or through the contextual menu from the Task List view.

When a task is activated, all the contents of the navigator will disappear. As you open new files, and click into new functions/methods, Mylyn will remember where you've been. As you move away from your original file, they'll start to drop off again unless you re-visit them; in which case, they grow stronger. It's also possible to mark a method as a 'landmark', which means it's permanently attached to that task.

The benefit of using this task approach is that you can very quickly filter the objects that you're working on and then revisit them later. Indeed, you can have multiple tasks and switch between them - the user interface will remember which files you had open at the time and re-open them for you.

Here's a screenshots showing an Objective C project without a Mylyn task active:

<img src='http://objectiveclipse.googlecode.com/svn/images/ObjectivEClipseWithoutMylyn.png' align='center' alt='ObjectivEClipse without Mylyn on an Objective C project' width='90%' />

Here's the same Objective C project with a Mylyn task active:

<img src='http://objectiveclipse.googlecode.com/svn/images/ObjectivEClipseWithMylyn.png' align='center' alt='ObjectivEClipse with Mylyn on an Objective C project' width='90%' />