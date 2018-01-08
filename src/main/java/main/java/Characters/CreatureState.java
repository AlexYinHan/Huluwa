package main.java.Characters;

public enum CreatureState {
    ATTACKING, MOVING, IDLE;

    RenderCreatureComponent.ImageType getImageType() {
        RenderCreatureComponent.ImageType imageType;
        switch (this) {
            case MOVING: imageType = RenderCreatureComponent.ImageType.MOVING; break;
            case ATTACKING: imageType = RenderCreatureComponent.ImageType.ATTACKING; break;
            case IDLE: imageType = RenderCreatureComponent.ImageType.IDLE; break;
            default: imageType = RenderCreatureComponent.ImageType.IDLE; break;
        }
        return imageType;
    }
}
