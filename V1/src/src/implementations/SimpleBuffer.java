package implementations;

import Interfaces.Buffer;

public class SimpleBuffer implements Buffer {
    /****************************************************************************************************/
    /*                                            Attributes                                            */
    /****************************************************************************************************/
    // Instance of SimpleBuffer
    private static volatile SimpleBuffer instance;
    // Content string
    private String content;

    /****************************************************************************************************/
    /*                                            Constructor                                           */
    /****************************************************************************************************/
    private SimpleBuffer(){
        content = "";
    }

    public static SimpleBuffer getInstance(){
        if(instance==null){ instance = new SimpleBuffer();}
        return instance;
    }

    /****************************************************************************************************/
    /*                                              Methods                                             */
    /****************************************************************************************************/
    @Override
    public void addContent(String s, int position) {
        // if content is empty, insert directly
        if(content == ""){
            content+=s;
        }
        else {
            // create empty string
            String tempString = "";

            // add char from 0 to insert position
            for(int i=0; i<position; i++){
                tempString+=content.charAt(i);
            }

            // insert string
            tempString+=s;

            // add the rest of content
            for(int i=position; i<content.length(); i++){
                tempString+=content.charAt(i);
            }

            // update content
            content = tempString;
        }
    }

    @Override
    public void deleteContent(int start, int stop) {
        // make sure content is not empty
        if(content!="") {
            // make sure start<stop
            if (start > stop) {
                int tmp = stop;
                stop = start;
                start = tmp;
            }

            // create empty string
            String tempString = "";

            // insert all char before start
            for(int i=0; i<start; i++){
                tempString+=content.charAt(i);
            }

            // insert all char after stop
            for(int i=stop; i<content.length(); i++){
                tempString+=content.charAt(i);
            }
            content = tempString;
        }
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getContent(int start, int stop) {
        // make sure start<stop
        if (start > stop) {
            int tmp = stop;
            stop = start;
            start = tmp;
        }

        // make sure stop<= content.length
        if(content.length()<=stop){stop=content.length()-1;}

        // if start > content.length => return empty string
        if(content.length()<=start){return "";}

        // create empty string
        String result = "";

        //insert all char from start to stop
        for(int i=start; i<=stop; i++){
            result += content.charAt(i);
        }

        return result;
    }
}
