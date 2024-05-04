package comp20050sep2.group1;

import comp20050sep2.group1.utils.Vector2D;

public class OptionMuteButton extends AbstractButton{

    /**
     * Constructs mute button
     * @param pos Position of mute button
     * @param text Mute button text
     */
    public OptionMuteButton(Vector2D pos, String text) {
        super(pos, text);
        
    }

    @Override
    public void performAction() {
        OptionsPanel.get().muted = !OptionsPanel.get().muted;

        this.text = OptionsPanel.get().muted ? "Unmute audio" : "Mute audio";

        MainMenuPanel.get().playSound("/boom.wav");
        MainMenuPanel.get().updateBGMVolume();
    }
    
}
