package com.frismos.isotest;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.ArrayList;

/**
 * Created by karen on 3/28/17.
 */
public class Play implements Screen {

    TiledMap map;
    IsometricTiledMapRenderer renderer;
    OrthographicCamera camera;
    Stage stage;

    Matrix4 isoTransform;
    Matrix4 invIsotransform;

    TiledMapTileLayer tempLayer;
    TiledMapTileLayer groundLayer;
    SpineActor mainImage;
    TextureRegion region;

    @Override
    public void show() {
        map = new TmxMapLoader().load("Olymp2.tmx");

        TiledMapImageLayer layer = (TiledMapImageLayer)map.getLayers().get(0);
        tempLayer = (TiledMapTileLayer)map.getLayers().get(5);
        layer.setX(6000);
        layer.setY(-3170);

        //StretchViewport viewport = new StretchViewport(32*16,64*32);
        stage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new OrthographicCamera()),
                new PolygonSpriteBatch());


        groundLayer = (TiledMapTileLayer)map.getLayers().get(1);

        renderer = new IsometricTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.position.x = 6000 + layer.getTextureRegion().getRegionWidth()/2;
        camera.position.y = 0;
        ArrayList<PolygonMapObject> objects = new ArrayList<PolygonMapObject>();

        Matrix4 stageMatrix = new Matrix4();
        stageMatrix.idt();

        isoTransform = new Matrix4();
        isoTransform.idt();

        isoTransform.scale((float)(Math.sqrt(2.0) ), (float)(Math.sqrt(2.0) /2.0), 1.0f);
        isoTransform.rotate(0.0f, 0.0f, 1.0f, -45);

        invIsotransform = new Matrix4(isoTransform);
        invIsotransform.inv();

        stage.getCamera().rotate(stageMatrix);


        Texture texture = new Texture("Path.png");
        region = new TextureRegion(texture);


//        mainImage = new Image(texture);
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skeleton.atlas"));
        mainImage = new SpineActor("skeleton.json", atlas, 1);
//        mainImage.setColor(Color.RED);
        mainImage.setAnimation(0, "animation", true);
        stage.addActor(mainImage);

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
            camera.position.x += 10*camera.zoom ;
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            camera.position.x -= 10*camera.zoom ;
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            camera.position.y += 10*camera.zoom ;
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            camera.position.y -= 10*camera.zoom ;
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.zoom+=1;
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.zoom-=1;
        if(!(camera.zoom > 0))
        {
            camera.zoom = 1;
        }
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
                if(groundLayer.getCell(x,y) != null  && groundLayer.getCell(x+1,y) != null && groundLayer.getCell(x,y+1) != null && groundLayer.getCell(x+1,y+1) != null
                        && tempLayer.getCell(x,y) == null && tempLayer.getCell(x+1,y) == null && tempLayer.getCell(x,y+1) == null && tempLayer.getCell(x+1,y+1) == null) {
                    //tempLayer.getCell(x, y).setTile(new StaticTiledMapTile(region));
                    TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                    cell.setTile(new StaticTiledMapTile(region));
                    TiledMapTileLayer.Cell emptyCell = new TiledMapTileLayer.Cell();
                    tempLayer.setCell(x,y,cell);
                    tempLayer.setCell(x+1,y,emptyCell);
                    tempLayer.setCell(x+1,y+1,emptyCell);
                    tempLayer.setCell(x,y+1,emptyCell);
                }

            }
        }

        if(Gdx.input.isTouched(0) && Gdx.input.getX() > 0 && Gdx.input.getY() > 0)
        {
            Vector3 curpos = new Vector3(Gdx.input.getX() , Gdx.input.getY() , 0);
            camera.unproject(curpos);
            mainImage.setPosition(curpos.x, curpos.y);
        }

        stage.getCamera().position.x = camera.position.x;
        stage.getCamera().position.y = camera.position.y;
        stage.getCamera().far = camera.zoom;
        stage.act(delta);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        stage.draw();
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
