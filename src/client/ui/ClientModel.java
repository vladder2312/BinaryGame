package client.ui;

import java.util.Arrays;

public class ClientModel {

    private boolean[] selected = {false, false, false, false, false, false, false, false};
    private boolean[] right;

    public void switchSelected(int position){
        selected[position] = !selected[position];
    }

    public void clearSelected() {
        Arrays.fill(selected, false);
    }

    public void setRight(String binaryString) {
        boolean[] right = {false, false, false, false, false, false, false, false};
        StringBuilder line = new StringBuilder(binaryString);
        while(line.length()<8){
            line.insert(0, "0");
        }
        char[] chars = line.toString().toCharArray();
        for(int i=0;i<8;i++){
            right[i] = chars[i]!='0';
        }
        this.right = right;
    }

    public boolean checkRight(){
        for(int i=0;i<right.length;i++){
            if(right[i]!=selected[i]){
                return false;
            }
        }
        return true;
    }
}
