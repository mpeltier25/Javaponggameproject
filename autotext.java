
public class autotext implements Runnable {
    
    private Thread t;
    private String hostText = new String("localhost");	
    private revamppong gui;
    private int frequency = 80;	
    private boolean looping = true;	
        
    
    public autotext(revamppong gui) {
        this.gui = gui;
        t = new Thread(this);
        t.start();
    }
    public void run() {
        
        boolean doAddress = true;
        boolean doTitle = true;
        int i = 0;
        
        while(looping) {
            
            try {
                Thread.sleep(frequency);
            }
            catch(InterruptedException e) {
            }
            
            try {
                if(doAddress)
                    gui.setRemoteAdrFieldText( hostText.valueOf(hostText.charAt(i)));
            }		
            catch(StringIndexOutOfBoundsException e) {
                doAddress = false;
            }
            i++; 
            
            if(!doTitle && !doAddress)
                looping = false;
        }
            
    }
    
}