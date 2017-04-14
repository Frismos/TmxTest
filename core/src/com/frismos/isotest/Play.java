package com.frismos.isotest;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

/**
 * Created by karen on 3/28/17.
 */
public class Play implements Screen {

    TiledMap map;
    IsometricTiledMapRenderer renderer;
    OrthographicCamera camera;
    World world;
    Box2DDebugRenderer b2dr;
    Stage stage;

    Matrix4 isoTransform;
    Matrix4 invIsotransform;

    TiledMapTileLayer tempLayer;
    Image mainImage;
    TextureRegion region;

    @Override
    public void show() {
        map = new TmxMapLoader().load("test4.tmx");

        //StretchViewport viewport = new StretchViewport(32*16,64*32);
        stage = new Stage();

        tempLayer = (TiledMapTileLayer)map.getLayers().get(1);

        renderer = new IsometricTiledMapRenderer(map);

        camera = new OrthographicCamera();
        ArrayList<PolygonMapObject> objects = new ArrayList<PolygonMapObject>();

        Matrix4 stageMatrix = new Matrix4();
        stageMatrix.idt();

        isoTransform = new Matrix4();
        isoTransform.idt();

        //isoTransform.translate(0, 32, 0);
        isoTransform.scale((float)(Math.sqrt(2.0) ), (float)(Math.sqrt(2.0) /2.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();

        stage.getCamera().rotate(stageMatrix);

        world = new World(new Vector2(0,0),true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        Texture texture = new Texture("water.png");
         region = new TextureRegion(texture);


        mainImage = new Image(texture);
        stage.addActor(mainImage);

        /*for(PolygonMapObject obj: map.getLayers().get(3).getObjects().getByType(PolygonMapObject.class))
        {
            Polygon poly = obj.getPolygon();

            bdef.type = BodyDef.BodyType.StaticBody;
            {
                Vector3 temp = new Vector3(poly.getX(), poly.getY(),0);
                Vector2 transformed = isoToWorld(temp);

                bdef.position.set(transformed.x, transformed.y);
                Image image = new Image(texture);
                image.setPosition(transformed.x,transformed.y);
                stage.addActor(image);
            }
            body = world.createBody(bdef);


            float[] vertices = poly.getVertices();
            float[] transformedVertices = new float[vertices.length];
            for(int i = 0 ; i < vertices.length ; i+=2)
            {
                Vector3 temp = new Vector3(vertices[i] , vertices[i+1] , 0);
                Vector2 transformed = isoToWorld(temp);
                transformedVertices[i] = transformed.x;
                transformedVertices[i+1] = transformed.y;
            }

 //           shape.set(transformedVertices);

            int count = shape.getVertexCount();

            fdef.shape = shape;

            body.createFixture(fdef);

            objects.add(obj);
        }*/
    }

    private Vector3 worldToIso(Vector3 point, int tileWidth, int tileHeight) {
        camera.unproject(point);
        point.x /= tileWidth;
        point.y = (point.y - tileHeight / 2) / tileHeight + point.x;
        point.x -= point.y - point.x;
        return point;
    }

    private Vector2 isoToWorld(Vector3 point)
    {
        point.mul(isoTransform);
        return new Vector2(point.x,point.y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(Gdx.input.isKeyPressed(Input.Keys.D))
            camera.position.x += 10 ;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x -= 10 ;
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += 10 ;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y -= 10 ;
        if(Gdx.input.isTouched())
        {
            Vector3 tempVector = new Vector3(Gdx.input.getX() , Gdx.input.getY(),0);
            camera.unproject(tempVector);
            tempVector.mul(invIsotransform);
            int x = (int)(tempVector.x*2/(tempLayer.getTileWidth()));
            int y = (int)(tempVector.y/(tempLayer.getTileHeight()));
            Gdx.app.setLogLevel(Application.LOG_DEBUG);
            Gdx.app.debug("Temp LOG" , "X == " + x + " ; Y == " + y);
            if(x>0 && y > 0) {
                tempLayer.getCell(x, y).getTile().setTextureRegion(region);
            }
        }

        if(Gdx.input.getX() > 0 && Gdx.input.getY() > 0)
        {
            Vector3 curpos = new Vector3(Gdx.input.getX() , Gdx.input.getY() , 0);
            camera.unproject(curpos);
            mainImage.setPosition(curpos.x, curpos.y);
        }

        stage.getCamera().position.x = camera.position.x;
        stage.getCamera().position.y = camera.position.y;
        stage.act(delta);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        stage.draw();

        b2dr.render(world,camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        stage.getCamera().viewportWidth = width;
        stage.getCamera().viewportHeight = height;
        stage.getCamera().update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
