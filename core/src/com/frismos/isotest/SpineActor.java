package com.frismos.isotest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonBounds;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.esotericsoftware.spine.attachments.BoundingBoxAttachment;
import com.esotericsoftware.spine.utils.SkeletonActor;

import static com.badlogic.gdx.utils.Align.bottom;
import static com.badlogic.gdx.utils.Align.left;
import static com.badlogic.gdx.utils.Align.right;
import static com.badlogic.gdx.utils.Align.top;

/**
 * Created by nelliminasyan on 5/3/17.
 */

public class SpineActor  extends SkeletonActor {
    public static final String SKELETON_FILE_NAME = "skeleton.json";
    protected static final float ANIMATION_MIX_TIME = 0.4f;
    //    protected SkeletonData skeletonData;
    protected BoundingBoxAttachment hit;
    protected Skeleton skeleton;
    protected SkeletonData skeletonData;
    protected SkeletonBounds skeletonBounds;
    protected AnimationState state;
    protected AnimationStateData stateData;
    //    protected SkeletonRendererDebug debugRenderer;
//    private boolean debug;
    final Vector2 tmp = new Vector2();

    private String jsonPath;

    public SpineActor(){}

    public SpineActor(String jsonPath, TextureAtlas atlas, float scale) {
        this.jsonPath = jsonPath;
        // TODO Nelli
//        skeletonData = loadSkeletonData(jsonPath, scale);

        SkeletonJson skeletonJson = new SkeletonJson(atlas);
        skeletonJson.setScale(scale);
        skeletonData = skeletonJson.readSkeletonData(Gdx.files.internal(jsonPath));

//        if(renderer == null){
//            renderer = new SkeletonMeshRenderer();
//        }
////        renderer.setPremultipliedAlpha(true); // PMA results in correct blending without outlines.
////        debugRenderer = new SkeletonRendererDebug();
////        debugRenderer.setBones(true);
////        debugRenderer.setRegionAttachments(true);
////        debugRenderer.setBoundingBoxes(true);
////        debugRenderer.setMeshHull(true);
////        debugRenderer.setMeshTriangles(true);
//
//
//        skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
//        //skeleton.setPosition(250, 20);
////		skeleton.setPosition(580 * Test1_Box2d.WORLD_TO_SCREEN, 184 * Test1_Box2d.WORLD_TO_SCREEN);
//
//        skeletonBounds = new SkeletonBounds(); // Convenience class to do hit detection with bounding boxes.
//
//        stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
////		stateData.setMix("run", "jump", 0.2f);
////		stateData.setMix("jump", "run", 0.2f);
//        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
////		state.setTimeScale(0.5f); // Slow all animations down to 50% speed.
//
//        // Queue animations on track 0.
////		state.setAnimation(0, "run", true);
////		state.addAnimation(0, "jump", false, 2); // Jump after 2 seconds.
////		state.addAnimation(0, "run", true, 0); // Run after the jump.


        init(skeletonData);
        initSomeData();

    }

    public SpineActor(SkeletonData skeletonData) {
//        if(renderer == null){
//            renderer = new SkeletonMeshRenderer();
//        }
//
//        this.skeletonData = skeletonData;
//
//        skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
//        //skeleton.setPosition(250, 20);
////		skeleton.setPosition(580 * Test1_Box2d.WORLD_TO_SCREEN, 184 * Test1_Box2d.WORLD_TO_SCREEN);
//
//        skeletonBounds = new SkeletonBounds(); // Convenience class to do hit detection with bounding boxes.
//
//        stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
////		stateData.setMix("run", "jump", 0.2f);
////		stateData.setMix("jump", "run", 0.2f);
//        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
////		state.setTimeScale(0.5f); // Slow all animations down to 50% speed.
//
//        // Queue animations on track 0.
////		state.setAnimation(0, "run", true);
////		state.addAnimation(0, "jump", false, 2); // Jump after 2 seconds.
////		state.addAnimation(0, "run", true, 0); // Run after the jump.


        this.skeletonData = skeletonData;
        init(this.skeletonData);

        initSomeData();

    }

    private void init(SkeletonData skeletonData_) {

        skeleton = new Skeleton(skeletonData_); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
        //skeleton.setPosition(250, 20);
//		skeleton.setPosition(580 * Test1_Box2d.WORLD_TO_SCREEN, 184 * Test1_Box2d.WORLD_TO_SCREEN);

        skeletonBounds = new SkeletonBounds(); // Convenience class to do hit detection with bounding boxes.

        stateData = new AnimationStateData(skeletonData_); // Defines mixing (crossfading) between animations.
//		stateData.setMix("run", "jump", 0.2f);
//		stateData.setMix("jump", "run", 0.2f);

        // mixing animations for smooth transition between them
//        Array<Animation> animations = skeletonData_.getAnimations();
//        for (int i = 0; i < animations.size - 1; i++) {
//            Animation anim1 = animations.get(i);
//            for (int j = i + 1; j < animations.size; j++) {
//                Animation anim2 = animations.get(j);
//
//                stateData.setMix(anim1.getName(), anim2.getName(), ANIMATION_MIX_TIME);
//                stateData.setMix(anim2.getName(), anim1.getName(), ANIMATION_MIX_TIME);
//            }
//        }

        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
//		state.setTimeScale(0.5f); // Slow all animations down to 50% speed.

        // Queue animations on track 0.
//        state.setAnimation(0, "animation", true);
//		state.addAnimation(0, "jump", false, 2); // Jump after 2 seconds.
//		state.addAnimation(0, "run", true, 0); // Run after the jump.


        if (getRenderer() == null) {
            setRenderer(new SkeletonMeshRenderer());
        }
        setSkeleton(skeleton);
        setAnimationState(state);

    }

    protected void initSomeData(){
        super.setScale(skeleton.getRootBone().getScaleX(), skeleton.getRootBone().getScaleY());
        super.setRotation(skeleton.getRootBone().getRotation());
        super.setPosition(skeleton.getX(), skeleton.getY());

        skeleton.updateWorldTransform();
        skeletonBounds.update(skeleton, true);
    }

    protected void setToSetupPose() {
//        float scaleX = skeleton.getRootBone().getScaleX();
//        float scaleY = skeleton.getRootBone().getScaleY();
        skeleton.setToSetupPose();
//        skeleton.getRootBone().setScaleX(scaleX);
//        skeleton.getRootBone().setScaleY(scaleY);
    }
//    protected SpineActor(Skeleton skeleton){
//        if(renderer == null){
//            renderer = new SkeletonMeshRenderer();
//        }
//
//        this.skeleton = skeleton; // Skeleton holds skeleton state (bone positions, slot attachments, etc).
//        //skeleton.setPosition(250, 20);
////		skeleton.setPosition(580 * Test1_Box2d.WORLD_TO_SCREEN, 184 * Test1_Box2d.WORLD_TO_SCREEN);
//
//        skeletonBounds = new SkeletonBounds(); // Convenience class to do hit detection with bounding boxes.
//
//        stateData = new AnimationStateData(skeleton.getData()); // Defines mixing (crossfading) between animations.
////		stateData.setMix("run", "jump", 0.2f);
////		stateData.setMix("jump", "run", 0.2f);
//        state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
////		state.setTimeScale(0.5f); // Slow all animations down to 50% speed.
//
//        // Queue animations on track 0.
////		state.setAnimation(0, "run", true);
////		state.addAnimation(0, "jump", false, 2); // Jump after 2 seconds.
////		state.addAnimation(0, "run", true, 0); // Run after the jump.
//
//
//        skeletonBounds.update(skeleton, true);
//
//        super.setScale(skeleton.getRootBone().getScaleX(), skeleton.getRootBone().getScaleY());
//        super.setRotation(skeleton.getRootBone().getRotation());
//        super.setPosition(skeleton.getX(), skeleton.getY());
//    }
//    public boolean isDebug() {
//        return debug;
//    }
//
//    @Override
//    public void setDebug(boolean debug) {
//        this.debug = debug;
//    }

// TODO Nelli
//    static SkeletonData loadSkeletonData(String jsonPath, float scale) {
//        SkeletonDataLoader.SkeletonDataLoaderParameter skeletonDataLoaderParameter = new SkeletonDataLoader.SkeletonDataLoaderParameter();
//        skeletonDataLoaderParameter.atlasName = AssetDataManager.$().ATLAS;
//        skeletonDataLoaderParameter.scale = scale;
//        AssetDataManager.$().getAssetManager().setLoader(SkeletonData.class, new SkeletonDataLoader(AssetDataManager.$().getDefaultResolver()));
//        AssetDataManager.$().getAssetManager().load(jsonPath, SkeletonData.class, skeletonDataLoaderParameter);
//        AssetDataManager.$().getAssetManager().finishLoadingAsset(jsonPath);
//
//        //        SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
////        json.setScale(scale); // json.setScale(0.6f); Load the skeleton at 60% the size it was in Spine.
////        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(jsonPath));
//
//        return AssetDataManager.$().getAssetManager().get(jsonPath);
//    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() != Touchable.enabled) return null;
        tmp.set(x, y);
        localToParentCoordinates(tmp);
//        skeleton.updateWorldTransform();
        skeletonBounds.update(skeleton, false); // Update SkeletonBounds with current skeleton bounding box positions.
//        if (skeletonBounds.aabbContainsPoint(tmp.x, tmp.y)) {
//            Gdx.app.debug("myLog", "map hit");
        hit = skeletonBounds.containsPoint(tmp.x, tmp.y); // Check if inside a bounding box.
        if (hit != null) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        skeleton.getRootBone().setScale(scaleX, scaleY);
        super.setScale(scaleX, scaleY);
    }

    @Override
    public void setScale(float scaleXY) {
        skeleton.getRootBone().setScale(scaleXY);
        super.setScale(scaleXY);
    }

    @Override
    public void setScaleX(float scaleX) {
        skeleton.getRootBone().setScaleX(scaleX);
        super.setScaleX(scaleX);
    }

    @Override
    public void setScaleY(float scaleY) {
        skeleton.getRootBone().setScaleY(scaleY);
        super.setScaleY(scaleY);
    }

    @Override
    public void scaleBy(float scale) {
        skeleton.getRootBone().setScale(skeleton.getRootBone().getScaleX() + scale, skeleton.getRootBone().getScaleY() + scale);
        super.scaleBy(scale);
    }

    @Override
    public void scaleBy(float scaleX, float scaleY) {
//        skeleton.getData().getWidth()
        skeleton.getRootBone().setScale(skeleton.getRootBone().getScaleX() + scaleX, skeleton.getRootBone().getScaleY() + scaleY);
        super.scaleBy(scaleX, scaleY);
    }

    @Override
    public void setRotation(float degrees) {
        skeleton.getRootBone().setRotation(degrees);
        super.setRotation(degrees);
    }

    @Override
    public void rotateBy(float amountInDegrees) {
        skeleton.getRootBone().setRotation(skeleton.getRootBone().getRotation() + amountInDegrees);
        super.rotateBy(amountInDegrees);
    }


    @Override
    public void setWidth(float width) {
        float scaleX = width / getWidth();
        setScaleX(scaleX * getScaleX());
        super.setWidth(width);
    }

    @Override
    public float getWidth() {
        skeleton.updateWorldTransform();
        skeletonBounds.update(skeleton, true);
        return skeletonBounds.getWidth();
    }

    @Override
    public void setHeight(float height) {
        float scaleY = height / getHeight();
        setScaleY(scaleY * getScaleY());
        super.setHeight(height);
    }

    @Override
    public float getHeight() {
        skeleton.updateWorldTransform();
        skeletonBounds.update(skeleton, true);
        return skeletonBounds.getHeight();
    }

    @Override
    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
        super.setSize(width, height);
    }

    public void setSize_(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    @Override
    public void sizeBy(float size) {
        if (size != 0) {
            setSize(getWidth() + size, getHeight() + size);
        }
        super.sizeBy(size);
    }

    @Override
    public void sizeBy(float width, float height) {
        if (width != 0 || height != 0) {
            setSize(getWidth() + width, getHeight() + height);
        }
        super.sizeBy(width, height);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        if ((alignment & right) != 0)
            x -= getWidth();
        else if ((alignment & left) == 0) //
            x -= getWidth() / 2;

        if ((alignment & top) != 0)
            y -= getHeight();
        else if ((alignment & bottom) == 0) //
            y -= getHeight() / 2;

        if (getX() != x || getY() != y) {
            setX(x);
            setY(y);
            positionChanged();
        }
    }

    @Override
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    @Override
    public void setX(float x) {
        if (getX() != x) {
            skeleton.setX(x);
            super.setX(x);
        }
    }

    @Override
    public float getX(int alignment) {
        float x = getX();
        if ((alignment & right) != 0)
            x += getWidth();
        else if ((alignment & left) == 0) //
            x += getWidth() / 2;
        return x;
    }

    @Override
    public void setY(float y) {
        if (getY() != y) {
            skeleton.setY(y);
            super.setY(y);
        }
    }

    @Override
    public float getY(int alignment) {
        float y = getY();
        if ((alignment & top) != 0)
            y += getHeight();
        else if ((alignment & bottom) == 0) //
            y += getHeight() / 2;
        return y;
    }

    @Override
    public void moveBy(float x, float y) {
        if (x != 0 || y != 0) {
            setX(getX() + x);
            setY(getY() + y);
        }
    }

    @Override
    public void setOrigin(int alignment) {
        // ignore
    }

    @Override
    public void setOrigin(float originX, float originY) {
        // ignore
    }

    @Override
    public void setOriginX(float originX) {
        // ignore
    }

    @Override
    public void setOriginY(float originY) {
        // ignore
    }

    @Override
    public void setBounds(float x, float y, float width, float height) {
        if (getX() != x || getY() != y) {
            setX(x);
            setY(y);
            positionChanged();
        }
        if (getWidth() != width || getHeight() != height) {
            setWidth(width);
            setHeight(height);
            sizeChanged();
        }
        super.setBounds(x, y, width, height);
    }

//    @Override
//    public void act(float delta) {
//        super.act(delta);
//
//        state.update(delta); // Update the animation time.
//        state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
//        skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.
//    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        renderer.draw((PolygonSpriteBatch) batch, skeleton); // Draw the skeleton images.
////        if(debug){
////            debugRenderer.draw(skeleton); // Draw debug lines.
////        }
//    }

    public String getSlotAttachmentName(String slotName) {
        return skeleton.findSlot(slotName).getAttachment().getName();
    }

    // TODO Nelli
//    public void dispose() {
//        if (jsonPath != null) {
//            AssetDataManager.$().getAssetManager().unload(jsonPath);
//        }
//    }

    public void updateWorldransform() {
        skeleton.updateWorldTransform();
    }

    public void setAnimation(int trackIndex, String animationName, boolean loop){
        state.setAnimation(trackIndex, animationName, loop);
    }
}
