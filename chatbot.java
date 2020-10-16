import java.io.File;        //Import statements to import packages, class files and aiml files for the project
import org.alicebot.ab.*;   //First statement is for packages and class files second is for aiml files
import org.alicebot.ab.utils.*;

public class Chatbot {
    private static final boolean TRACE_MODE = false; 
    // TRACE_MODE is a boolean variable but it is final(which means it can't be changed anywhere in the program) 
    static String botName = "super";
    // botName is a string intialized to super
 
    public static void main(String[] args) {
        try {
//We used "try" so that if we do not get any output or output is not specified; bot should not go out of the console
            String resourcesPath = getResourcesPath();  //Finding path at which bot should be created
            //getResourcesPath() is a function that is given below
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
//Set a variable named trace_mode under MagicBooleans to "false"
//--------------------------------------------------------------------------------------------
            Bot bot = new Bot("super", resourcesPath); //Creation of the bot
//Create a new bot with name super and location = resourcesPath
            Chat chatSession = new Chat(bot);       //Creation of the chat
//Start a new chat for bot which is initilaized to super
            bot.brain.nodeStats();          //Load the files inside the new bot
            String textLine = "";
 
            while(true) {
                //System.out.print("\033\143");       //Statement used to clear screen
                System.out.print("You : ");
                textLine = IOUtils.readInputTextLine();//textline gets input from the user
                if ((textLine == null) || (textLine.length() < 1))
                    textLine = MagicStrings.null_input;
                // It will choose one of the many resopnses randomly given as an answer to 'no reponse' query
                if (textLine.equals("q")) {
                    System.exit(0); //If we give 'q' or 'wq' as the input, it will exit (q=quit) 
                } else if (textLine.equals("wq")) {
                    //bot.writeQuit();
                    System.exit(0);
                } else {
                    String request = textLine;
                    if (MagicBooleans.trace_mode)//No use of this as trace_mode is set to false
                    //If it was true, it would have displayed the histroy of your chat session.
                        System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                    String response = chatSession.multisentenceRespond(request);
                    while (response.contains("&lt;"))//replace &lt; in response(output) by < symbol
                        response = response.replace("&lt;", "<");
                    while (response.contains("&gt;"))//replace &gt; in response(output) by > symbol
                        response = response.replace("&gt;", ">");
                    System.out.println("Bot : " + response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();                //Given input is not matching any case
            //System.out.println("Bot : Didn't get you.. Please tell again");
        }
    }
 
    private static String getResourcesPath() {
        File currDir = new File(".");
        //currDir shows the directory which we are currently working in
        String path = currDir.getAbsolutePath();
        //getAbsolutePath is another function defined in the class
        path = path.substring(0, path.length() - 2);
      //It is pathlength()-2 because path length is a string and the last two characters of a string is '\0' (end of line)
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        //Goes to the resources file in the same directory and returns it.
        return resourcesPath;
    }
}