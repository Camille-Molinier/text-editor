package implementations;
import Interfaces.Buffer;

public class tester {

    public static int moveCursor(String s) {
        // assert text field only contains char
        boolean error = false;
        int caretPosition = s.length() - 1;
        for (int i = 0; i < s.length(); i++) {

            if (!(s.charAt(i) == '0' || s.charAt(i) == '1' || s.charAt(i) == '2' || s.charAt(i) == '3' || s.charAt(i) == '4' ||
                    s.charAt(i) == '5' || s.charAt(i) == '6' || s.charAt(i) == '7' || s.charAt(i) == '8' || s.charAt(i) == '9')) {
                caretPosition = 0;
                //jTextField.setText(caretPosition+"");
                error = true;
                //System.out.println(s.charAt(i));
                break;
            }
        }
        if(!error) {
            return  Integer.parseInt(s);
        }
        else return 0;
    }

    public static void main(String[] args) {
        Buffer b = SimpleBuffer.getInstance();

        b.addContent("balb lab lrhzihd", 0);
        System.out.println(b.getContent());

        b.deleteContent(b.getContent().length()-1, b.getContent().length());
        System.out.println(b.getContent());

    }


}
