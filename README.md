Ionita Cosmin 324 CA
@2022

*** Firstly, I want to get the complaints out of the way. Even if some of these were addresed on the forum, they are major and should have been put in the OCW task.
	-> I'm not sure implementing a database in java is the best way to learn OOP, since there a countless of other languages designed specifically for this, but I'm not as knowledgeable as older students/teachers, I could be wrong.
	-> The task has a mistake in the filter section. It says that the primary comparator is rating and then duration, but it's the exact opposite. Also, the way the outputs are formulated is misleading. You'd think you have to output for everything, but for example, you don't output anything for buy_tokens finishing succesfully. There's a rumor that the one who made the task is different from the one who wrote the code that generated the outputs, and that there was little to no communication involved. If that's true, that's the dumbest thing in the world.
	-> Last year, the deadline was until January, and there were NUMEROUS problems with the task on OCW, some got fixed, sure, but some, like those above weren't. So why couldn't we get an extension (for more than 2 days), then? There were various reasons for, and few (if any) that made sense against it. There was even an initial talk that we would get an extension before being denied randomly.
	-> The way the input file and output file are given. Sure it is on the forum, but it should have been the first thing to mention on OCW.
*** Now, let's get to the actual program, shall we? 
*** THE MAIN FLOW
*
*
	So I have the Main, which reads from the file stored in args[0] (technically not the file stored there, but the file's name so almost the same) into the creatively named input variable. I call ThePopcorn (https://www.youtube.com/watch?v=82ZCt7PZbj0) function, which is where the "problem solving" actually is. I make a for to access each command, then first check its type. Afterward, check if it's an available action for the current page. If not, we throw an error. Otherwise, keep going with the specific command. Afterward, update the output if necessary
*** The ChangePage method
	handles all switching to pages (with a switch, funnily enough). "Movie" needs to display the current movies, so we do that. "See details" needs to display the chosen movie, so we also do that, and "Logout" changes the page to unauthenticated and removes current user. Any other page just has the default of changing to said page.
*** The OnPageActions method
	handles all the onPage actions (https://www.youtube.com/watch?v=oCfbJZ0NeoA), with a switch again. Each command has its unique action, so no default needed (outside of coding style).
*** DETAILS ABOUT IMPLEMENTATION
*
*
*** Singletons
	The user database and the movie database are both singletons, since, in my opinion, it is more suitable for this type of program, where we keep the same database until the very end and we need to access it from different points. I also have the destroyDatabase function in each of them in case it is ever needed, but I don't use it.
*** Utility classes
	I have the utility classes Erros, OnPageActions and ChangePage, the Main itself, and WhereIs (funniest thing since https://www.youtube.com/watch?v=ML5UI-0JS_Q). I declared them like this because it's much easier (and imo makes more sense) to access them directly using the class instead of making an instance.
*** Other classes
	I also have regular classes, like Movie, User, Filter etc. They are just regular objects to hold information.
*** Conclusion
	This is the big picture of the program. For more details, there are comments in the code, and the code itself also provides some information.

	With all due respect, 
	https://www.youtube.com/watch?v=aMgCBYgVwsI

#     # ####### ######  #       ######     ####### ######  ###  #####   #####  ####### ######  
#  #  # #     # #     # #       #     #       #    #     #  #  #     # #     # #       #     # 
#  #  # #     # #     # #       #     #       #    #     #  #  #       #       #       #     # 
#  #  # #     # ######  #       #     #       #    ######   #  #  #### #  #### #####   ######  
#  #  # #     # #   #   #       #     #       #    #   #    #  #     # #     # #       #   #   
#  #  # #     # #    #  #       #     #       #    #    #   #  #     # #     # #       #    #  
 ## ##  ####### #     # ####### ######        #    #     # ###  #####   #####  ####### #     #  