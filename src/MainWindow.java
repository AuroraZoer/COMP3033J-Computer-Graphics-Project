import GraphicsObjects.Arcball;
import GraphicsObjects.Utils;
import objects3D.basic.Cylinder;
import objects3D.basic.DiffTexCube;
import objects3D.basic.TexCube;
import objects3D.basic.TexSphere;
import objects3D.human.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluLookAt;

//Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment. 
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment. 
// 

// Do not touch this class, I will be making a version of it for your 3rd Assignment 
public class MainWindow {

    private boolean MouseOnepressed = true;
    private boolean dragMode = false;
    /**
     * position of pointer
     */
    float x = 0, y = 0, z = 0;
    /**
     * angle of rotation
     */
    float rotation = 0;
    /**
     * time at last frame
     */
    long lastFrame;
    /**
     * frames per second
     */
    int fps;
    /**
     * last fps time
     */
    long lastFPS;

    long myDelta = 0; // to use for animation
    long StartTime; // beginAnimiation
    Arcball MyArcball = new Arcball();
    boolean focusTeachHuman = false;
    boolean focusShineHuman = false;
    boolean studentHuman = false;
    boolean keyRelease = true;
    /**
     * Mouse movement
     */
    int LastMouseX = -1;
    int LastMouseY = -1;

    int OrthoNumber = 0; // using this for screen size, making a window of 1200 x 800 so aspect ratio 3:2
    // // do not change this for assignment 3 but you can change everything for your
    // project
    boolean teacherFaceLeft = true;
    float teacherX = -700f;
    float earthRotation = 0f;

    float[] random = new float[]{(float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random(), (float) Math.random()};

    // basic colours
    static float black[] = {0.0f, 0.0f, 0.0f, 1.0f};
    static float white[] = {1.0f, 1.0f, 1.0f, 1.0f};

    static float grey[] = {0.5f, 0.5f, 0.5f, 1.0f};
    static float spot[] = {0.1f, 0.1f, 0.1f, 0.5f};

    // primary colours
    static float red[] = {1.0f, 0.0f, 0.0f, 1.0f};
    static float green[] = {0.0f, 1.0f, 0.0f, 1.0f};
    static float blue[] = {0.0f, 0.0f, 1.0f, 1.0f};

    // secondary colours
    static float yellow[] = {1.0f, 1.0f, 0.0f, 1.0f};
    static float magenta[] = {1.0f, 0.0f, 1.0f, 1.0f};
    static float cyan[] = {0.0f, 1.0f, 1.0f, 1.0f};

    // other colours
    static float orange[] = {1.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float brown[] = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float dkgreen[] = {0.0f, 0.5f, 0.0f, 1.0f, 1.0f};
    static float pink[] = {1.0f, 0.6f, 0.6f, 1.0f, 1.0f};

    // static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};

    // support method to aid in converting a java float array into a Floatbuffer
    // which is faster for the opengl layer to process

    public void start() {

        StartTime = getTime();
        try {
            Display.setDisplayMode(new DisplayMode(1200, 800));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        initGL(); // init OpenGL
        getDelta(); // call once before loop to initialise lastFrame
        lastFPS = getTime(); // call before loop to initialise fps timer

        while (!Display.isCloseRequested()) {
            int delta = getDelta();
            update(delta);
            renderGL();
            Display.update();
            Display.sync(120); // cap fps to 120fps
        }

        Display.destroy();
    }

    public void update(int delta) {
        // rotate quad
        // rotation += 0.01f * delta;
        int MouseX = Mouse.getX();
        int MouseY = Mouse.getY();
        int WheelPostion = Mouse.getDWheel();

        boolean MouseButtonPressed = Mouse.isButtonDown(0);

        if (MouseButtonPressed && !MouseOnepressed) {
            MouseOnepressed = true;
            MyArcball.startBall(MouseX, MouseY, 1200, 800);
            dragMode = true;

        } else if (!MouseButtonPressed) {
            MouseOnepressed = false;
            dragMode = false;
        }

        if (dragMode) {
            MyArcball.updateBall(MouseX, MouseY, 1200, 800);
        }

        if (WheelPostion > 0) {
            OrthoNumber += 1;
            if (OrthoNumber > 48) {
                OrthoNumber = 48;
            }
        }

        if (WheelPostion < 0) {
            OrthoNumber -= 1;
            if (OrthoNumber < -100) {
                OrthoNumber = -100;
            }
        }

        // R key to reset the view
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            MyArcball.reset();
            glMatrixMode(GL_MODELVIEW);
            glLoadIdentity();
            gluLookAt(-1000.0f, 1500.0f, -2000.0f, -1000.0f, 1500.0f, 0.0f, 0.0f, 1.0f, 0.0f);
            x = 0;
            y = 0;
            z = 0;
        }

        /* bad animation can be turned on or off using A key */

        if (Keyboard.isKeyDown(Keyboard.KEY_A))
            x += 0.4f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_D))
            x -= 0.4f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_W))
            z += 0.4f * delta;
        if (Keyboard.isKeyDown(Keyboard.KEY_S))
            z -= 0.4f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_Q))
            y += 0.4f * delta;

        if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
            y -= 0.4f * delta;
        }

        if (keyRelease) // check done to see if key is released
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
                focusTeachHuman = !focusTeachHuman;
                Keyboard.next();
                keyRelease = Keyboard.isKeyDown(Keyboard.KEY_Z);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
                focusShineHuman = !focusShineHuman;
                Keyboard.next();
                keyRelease = Keyboard.isKeyDown(Keyboard.KEY_X);
            } else if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
                studentHuman = !studentHuman;
                Keyboard.next();
                keyRelease = Keyboard.isKeyDown(Keyboard.KEY_C);

            }
        }

        // Check if key is released
        keyRelease = !Keyboard.isKeyDown(Keyboard.KEY_F);

        // keep quad on the screen
        if (x < -1200)
            x = -1200;
        if (x > 1200)
            x = 1200;
        if (y < -1000)
            y = -1000;
        if (y > 800)
            y = 800;

        updateFPS(); // update FPS Counter

        LastMouseX = MouseX;
        LastMouseY = MouseY;
    }

    /**
     * Calculate how many milliseconds have passed since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        changeOrth();
        MyArcball.startBall(0, 0, 1200, 800);
        glMatrixMode(GL_MODELVIEW);
        FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
        lightPos.put(10000f).put(0).put(-3000f).put(0).flip();

        FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
        lightPos2.put(0).put(10000f).put(-10000f).put(0).flip();

        FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
        lightPos3.put(0).put(10000f).put(-1000f).put(0).flip();

        FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
        lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

        glLight(GL_LIGHT0, GL_POSITION, lightPos); // specify the position of the light
        glEnable(GL_LIGHT0); // switch light #0 on // I've setup specific materials
        // so in real light it will look abit strange

        glLight(GL_LIGHT1, GL_POSITION, lightPos2); // specify the position of the light
        glEnable(GL_LIGHT1); // switch light #0 on
        glLight(GL_LIGHT1, GL_DIFFUSE, Utils.ConvertForGL(spot));

        glLight(GL_LIGHT2, GL_POSITION, lightPos3); // specify the position of the light
        glEnable(GL_LIGHT2); // switch light #0 on
        glLight(GL_LIGHT2, GL_DIFFUSE, Utils.ConvertForGL(grey));

        glEnable(GL_LIGHTING); // switch lighting on
        glEnable(GL_DEPTH_TEST); // make sure depth buffer is switched
        glDepthFunc(GL_LESS);// on
        glDepthMask(true);
        glEnable(GL_NORMALIZE); // normalize normal vectors for safety
        glEnable(GL_COLOR_MATERIAL);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        try {
            init();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void changeOrth() {
        GL11.glMatrixMode(GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glFrustum(-50.0 + OrthoNumber, 50.0 - OrthoNumber, (-50.0 + OrthoNumber) * 0.66f, (50.0 - OrthoNumber) * 0.66f, 100, 100000.0);
        GL11.glMatrixMode(GL_MODELVIEW);

        FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL_MODELVIEW_MATRIX, CurrentMatrix);

        MyArcball.getMatrix(CurrentMatrix);
        GL11.glLoadMatrix(CurrentMatrix);

        if (focusTeachHuman) {
            gluLookAt(-425f, 200f, -1000f, teacherX, 100f, 400f, 0.0f, 1.0f, 0.0f);
        } else if (focusShineHuman) {
            gluLookAt(100f, 150f, -1100f, 400f, 100f, 500f, 0.0f, 1.0f, 0.0f);
        } else if (studentHuman) {
            gluLookAt(-400f + x, 200f + y, 400f + z, teacherX, 100f + y, -500f + z, 0.0f, 1.0f, 0.0f);
        } else {
            gluLookAt(-400f + x, 1200f + y, -2000f + z, -400f + x, y, z, 0.0f, 1.0f, 0.0f);
        }
    }

    /**
     * Draws a textured cube at the specified position with the given scale and texture.
     *
     * @param posX    X-coordinate of the cube's position.
     * @param posY    Y-coordinate of the cube's position.
     * @param posZ    Z-coordinate of the cube's position.
     * @param scaleX  Scale factor along the X-axis.
     * @param scaleY  Scale factor along the Y-axis.
     * @param scaleZ  Scale factor along the Z-axis.
     * @param texture Texture to apply to the cube.
     */
    private void showTexCube(float posX, float posY, float posZ, float scaleX, float scaleY, float scaleZ, Texture texture) {
        GL11.glPushMatrix();
        TexCube MyGlobe = new TexCube();
        GL11.glTranslatef(posX, posY, -posZ);
        GL11.glScalef(scaleX / 2, scaleZ / 2, scaleY / 2);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        Color.white.bind();
        texture.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        MyGlobe.DrawTexCube();
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    /**
     * Draws a textured cube at the specified position with the given scale and textures.
     *
     * @param posX     X-coordinate of the cube's position.
     * @param posY     Y-coordinate of the cube's position.
     * @param posZ     Z-coordinate of the cube's position.
     * @param scaleX   Scale factor along the X-axis.
     * @param scaleY   Scale factor along the Y-axis.
     * @param scaleZ   Scale factor along the Z-axis.
     * @param textures Textures to apply to the cube.
     */
    private void showDiffTexCube(float posX, float posY, float posZ, float scaleX, float scaleY, float scaleZ, Texture[] textures) {
        GL11.glPushMatrix();
        DiffTexCube MyGlobe = new DiffTexCube();
        GL11.glTranslatef(posX, posY, -posZ);
        GL11.glScalef(scaleX / 2, scaleZ / 2, scaleY / 2);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        Color.white.bind();
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        MyGlobe.drawTexCube(textures);
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    /**
     * Draws a cylinder at the specified position with the given scale.
     *
     * @param posX   X-coordinate of the cylinder's position.
     * @param posY   Y-coordinate of the cylinder's position.
     * @param posZ   Z-coordinate of the cylinder's position.
     * @param scaleX Scale factor along the X-axis.
     * @param scaleY Scale factor along the Y-axis.
     * @param scaleZ Scale factor along the Z-axis.
     */
    private void showCylinder(float posX, float posY, float posZ, float scaleX, float scaleY, float scaleZ) {
        GL11.glPushMatrix();
        Cylinder cylinder = new Cylinder();
        GL11.glColor3f(0.9f, 0.9f, 0.9f);  // set the grey color to the cylinder
        GL11.glMaterialf(GL_FRONT, GL_SHININESS, 50f);
        GL11.glTranslatef(posX, posY, -posZ);
        GL11.glRotatef(90, 1, 0, 0);  // rotate the cylinder to make it stand up
        GL11.glScalef(scaleX / 2, scaleZ / 2, scaleY);
        cylinder.DrawCylinder(1.0f, 1.0f, 50);
        GL11.glPopMatrix();
    }

    /**
     * Draws a JumpHuman at the specified position with the given scale and textures.
     *
     * @param delta           delta
     * @param posX            X-coordinate of the human's position.
     * @param posY            Y-coordinate of the human's position.
     * @param posZ            Z-coordinate of the human's position.
     * @param jumpHumanFacing jumpHuman facing
     */
    private void showJumpHuman(float delta, float posX, float posY, float posZ, float jumpHumanFacing) {
        JumpHuman jumpHuman = new JumpHuman();
        GL11.glPushMatrix();
        float jumpHeight = 20f;
        float jumpSpeed = 64f;
        float jumpOffset = (float) Math.sin(delta * jumpSpeed) * jumpHeight;
        GL11.glTranslatef(posX, posY + jumpOffset, posZ);
        GL11.glRotatef(jumpHumanFacing, 0f, 1.0f, 0f);
        GL11.glScalef(45f, 45f, 45f);
        jumpHuman.DrawHuman(delta, shineFaceTexture, bowTieTexture, pelvisTexture, jumpHumanFacing);
        GL11.glPopMatrix();
    }

    private void showLearnHuman(float delta, float posX, float posY, float posZ) {
        LearnHuman learnHuman = new LearnHuman();
        GL11.glPushMatrix();
        float learnHumanFacing = 180f;
        GL11.glTranslatef(posX, posY, posZ);
        GL11.glRotatef(learnHumanFacing, 0f, 1.0f, 0f);
        GL11.glScalef(45f, 45f, 45f);
        learnHuman.DrawHuman(delta, smileFaceTexture, tieTexture, pelvisTexture, learnHumanFacing);
        GL11.glPopMatrix();
    }

    private void showSleepHuman(float delta, float posX, float posY, float posZ) {
        SleepHuman sleepHuman = new SleepHuman();
        GL11.glPushMatrix();
        float sleepHumanFacing = 180f;
        GL11.glTranslatef(posX, posY, posZ);
        GL11.glRotatef(sleepHumanFacing, 0f, 1.0f, 0f);
        GL11.glScalef(45f, 45f, 45f);
        sleepHuman.DrawHuman(delta, sleepFaceTexture, tieTexture, pelvisTexture, sleepHumanFacing);
        GL11.glPopMatrix();
    }

    /*
     * You can edit this method to add in your own objects / remember to load in
     * textures in the INIT method as they take time to load
     */
    public void renderGL() {
        changeOrth();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        glColor3f(0.5f, 0.5f, 1.0f);

        myDelta = getTime() - StartTime;
        float delta = ((float) myDelta) / 10000;


        // code to aid in animation
        float theta = (float) (delta * 2 * Math.PI);
        while (theta > 2 * Math.PI) {
            theta = (float) (theta - 2 * Math.PI);
        }
        float thetaDeg = delta * 360;
        while (thetaDeg > 1080) {
            thetaDeg = thetaDeg - 1080;
        }
        float posn_x = (float) Math.cos(theta); // same as your circle code in your notes
        float posn_y = (float) Math.sin(theta);

        // Background DiffTexCube
        GL11.glDisable(GL_LIGHTING);
        GL11.glPushMatrix();
        DiffTexCube background = new DiffTexCube();
        GL11.glTranslatef(x, y, z);
        GL11.glScalef(9999f, 9999f, 9999f);
        background.drawTexCube(new Texture[]{back1Texture, back5Texture, back3Texture, back2Texture, back0Texture, back4Texture});
        GL11.glPopMatrix();
        GL11.glEnable(GL_LIGHTING);

        // Floor TexCube
        GL11.glColor3f(white[0], white[1], white[2]);
        GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
        showTexCube(-400f, 0f, -100f, 2500f, 2500f, 1f, floorTexture);

        // Classroom walls TexCube
        float[][] walls = {
                {-400f, 200f, -500f, 1020f, 20f, 400f},
                {100f, 200f, -50f, 20f, 900f, 400f},
                {-900f, 200f, -50f, 20f, 900f, 400f}
        };

        for (float[] wall : walls) {
            showTexCube(wall[0], wall[1], wall[2], wall[3], wall[4], wall[5], wallTexture);
        }

        // Classroom tables Cylinder
        float[][] tables = {
                {-100f, 10f, 100f, 50f, 10f, 50f},
                {-100f, (5f + 65f), 100f, 10f, 60f, 10f},
                {-100f, (10f + 65f), 100f, 100f, 10f, 100f},
                {-400f, 10f, 100f, 50f, 10f, 50f},
                {-400f, (5f + 65f), 100f, 10f, 60f, 10f},
                {-400f, (10f + 65f), 100f, 100f, 10f, 100f},
                {-700f, 10f, 100f, 50f, 10f, 50f},
                {-700f, (5f + 65f), 100f, 10f, 60f, 10f},
                {-700f, (10f + 65f), 100f, 100f, 10f, 100f},
                {-250f, 10f, 300f, 50f, 10f, 50f},
                {-250f, (5f + 65f), 300f, 10f, 60f, 10f},
                {-250f, (10f + 65f), 300f, 100f, 10f, 100f},
                {-550f, 10f, 300f, 50f, 10f, 50f},
                {-550f, (5f + 65f), 300f, 10f, 60f, 10f},
                {-550f, (10f + 65f), 300f, 100f, 10f, 100f},
        };

        for (float[] table : tables) {
            showCylinder(table[0], table[1], table[2], table[3], table[4], table[5]);
        }

        // Computer
        showDiffTexCube(-100f, 75f, 100f, 60f, 30f, 1f, new Texture[]{lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, keyboardTexture});
        showDiffTexCube(-100f, 91f, 85f, 60f, 1f, 30f, new Texture[]{lightEdgeTexture, screenTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture});

        showDiffTexCube(-400f, 75f, 100f, 60f, 30f, 1f, new Texture[]{lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, keyboardTexture});
        showDiffTexCube(-400f, 91f, 85f, 60f, 1f, 30f, new Texture[]{lightEdgeTexture, screenTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture});

        showDiffTexCube(-700f, 75f, 100f, 60f, 30f, 1f, new Texture[]{lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, keyboardTexture});
        showDiffTexCube(-700f, 91f, 85f, 60f, 1f, 30f, new Texture[]{lightEdgeTexture, screenTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture});

        showDiffTexCube(-250f, 75f, 300f, 60f, 30f, 1f, new Texture[]{lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, keyboardTexture});
        showDiffTexCube(-250f, 91f, 285f, 60f, 1f, 30f, new Texture[]{lightEdgeTexture, screenTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture});

        showDiffTexCube(-550f, 75f, 300f, 60f, 30f, 1f, new Texture[]{lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, keyboardTexture});
        showDiffTexCube(-550f, 91f, 285f, 60f, 1f, 30f, new Texture[]{lightEdgeTexture, screenTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture, lightEdgeTexture});

        // Classroom chairs TexCube
        float[][] chairs = {
                {-425f, 20f, 170f, 10f, 10f, 50f},
                {-425f, 20f, 220f, 10f, 10f, 50f},
                {-375f, 20f, 220f, 10f, 10f, 50f},
                {-375f, 20f, 170f, 10f, 10f, 50f},
                {-400f, 40f, 195f, 60f, 60f, 10f},
                {-400f, 67f, 220f, 60f, 10f, 80f},

                {-125f, 20f, 170f, 10f, 10f, 50f},
                {-125f, 20f, 220f, 10f, 10f, 50f},
                {-75f, 20f, 220f, 10f, 10f, 50f},
                {-75f, 20f, 170f, 10f, 10f, 50f},
                {-100f, 40f, 195f, 60f, 60f, 10f},
                {-100f, 67f, 220f, 60f, 10f, 80f},

                {-725f, 20f, 170f, 10f, 10f, 50f},
                {-725f, 20f, 220f, 10f, 10f, 50f},
                {-675f, 20f, 220f, 10f, 10f, 50f},
                {-675f, 20f, 170f, 10f, 10f, 50f},
                {-700f, 40f, 195f, 60f, 60f, 10f},
                {-700f, 67f, 220f, 60f, 10f, 80f},

                {-275f, 20f, 370f, 10f, 10f, 50f},
                {-275f, 20f, 420f, 10f, 10f, 50f},
                {-225f, 20f, 420f, 10f, 10f, 50f},
                {-225f, 20f, 370f, 10f, 10f, 50f},
                {-250f, 40f, 395f, 60f, 60f, 10f},
                {-250f, 67f, 420f, 60f, 10f, 80f},

                {-575f, 20f, 370f, 10f, 10f, 50f},
                {-575f, 20f, 420f, 10f, 10f, 50f},
                {-525f, 20f, 420f, 10f, 10f, 50f},
                {-525f, 20f, 370f, 10f, 10f, 50f},
                {-550f, 40f, 395f, 60f, 60f, 10f},
                {-550f, 67f, 420f, 60f, 10f, 80f},
        };

        for (float[] chair : chairs) {
            showTexCube(chair[0], chair[1], chair[2], chair[3], chair[4], chair[5], tableTexture);
        }

        // Classroom platform TexCube
        float[][] platform = {
                {-200f, 50f, -320f, 30f, 30f, 80f},
                {-200f, 100f, -320f, 60f, 30f, 30f}
        };

        for (float[] floats : platform) {
            showTexCube(floats[0], floats[1], floats[2], floats[3], floats[4], floats[5], platformTexture);
        }

        // Classroom platform floor TexCube
        showTexCube(-400f, 10f, -400f, 1000f, 200f, 5f, tableTexture);

        // Classroom blackboard DiffTexCube
        showDiffTexCube(-400f, 200f, -490f, 400f, 10f, 200f, new Texture[]{woodEdgeTexture, blackboardTexture, woodEdgeTexture, woodEdgeTexture, woodEdgeTexture, woodEdgeTexture});

        // Classroom clock DiffTexCube
        showDiffTexCube(-400f, 340f, -490f, 60f, 10f, 60f, new Texture[]{woodEdgeTexture, clockTexture, woodEdgeTexture, woodEdgeTexture, woodEdgeTexture, woodEdgeTexture});

        // Classroom sound DiffTexCube
        float[][] sound = {
                {-150f, 250f, -480f, 50f, 20f, 80f},
                {-650f, 250f, -480f, 50f, 20f, 80f}
        };
        for (float[] floats : sound) {
            showDiffTexCube(floats[0], floats[1], floats[2], floats[3], floats[4], floats[5], new Texture[]{darkEdgeTexture, soundTexture, darkEdgeTexture, darkEdgeTexture, darkEdgeTexture, darkEdgeTexture});
        }

        // Classroom draw DiffTexCube
        float[][] draw = {
                {80f, 200f, -150f, 5f, 300f, 200f},
                {-880f, 200f, -150f, 5f, 300f, 200f}
        };
        for (float[] floats : draw) {
            showDiffTexCube(floats[0], floats[1], floats[2], floats[3], floats[4], floats[5], new Texture[]{lightEdgeTexture, lightEdgeTexture, texture2023, texture2023, lightEdgeTexture, lightEdgeTexture});
        }

        // Classroom earth Sphere
        showCylinder(-700f, 20f, -120f, 80f, 10f, 80f);
        GL11.glPushMatrix();
        TexSphere earthSphere = new TexSphere();
        GL11.glTranslatef(-700f, 80f, 130f);
        GL11.glRotatef(90f, 1f, 0, 0);
        GL11.glRotatef(earthRotation, 0, 0, 1f);
        earthRotation = (earthRotation + 1f) % 360f;
        GL11.glScalef(60f, 60f, 60f);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
        Color.white.bind();
        earthTexture.bind();
        glEnable(GL_TEXTURE_2D);
        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        earthSphere.DrawTexSphere(1f, 100, 100, earthTexture);
        GL11.glDisable(GL_TEXTURE_2D);
        GL11.glPopMatrix();
        // Classroom earth shadow
        Cylinder cylinder = new Cylinder();
        GL11.glColor4f(spot[0], spot[1], spot[2], spot[3]);
        GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(spot));
        GL11.glPushMatrix();
        GL11.glTranslatef(-790f, 1f, 120f);
        GL11.glRotatef(90f, 1f, 0, 0);
        GL11.glScalef(4f, 2f, 4f);
        cylinder.DrawCylinder(10f, 0.01f, 100);
        GL11.glPopMatrix();

        // TeachHuman
        GL11.glPushMatrix();
        TeachHuman myTeachHuman = new TeachHuman();
        float teachHumanFacing;
        if (teacherFaceLeft) {
            teachHumanFacing = -90f;
            teacherX = teacherX + 5f;
            GL11.glTranslatef(teacherX, 85f, 400f);
            GL11.glRotatef(teachHumanFacing, 0f, 1f, 0f);
            if (teacherX > 0f) {
                teacherFaceLeft = false;
            }
        } else {
            teachHumanFacing = 90f;
            teacherX = teacherX - 5f;
            GL11.glTranslatef(teacherX, 85f, 400f);
            GL11.glRotatef(teachHumanFacing, 0f, 1f, 0f);
            if (teacherX < -700f) {
                teacherFaceLeft = true;
            }
        }
        GL11.glScalef(45f, 45f, 45f);
        myTeachHuman.DrawHuman(delta, coolFaceTexture, tieTexture, pelvisTexture, teachHumanFacing);
        GL11.glPopMatrix();

        // JumpHuman
        showJumpHuman(delta, 150f, 85f, -500f, 10f);
        showJumpHuman(delta, -950f, 85f, -500f, -10f);
        showJumpHuman(delta, 250f, 85f, -600f, 20f);
        showJumpHuman(delta, -1050f, 85f, -600f, -20f);

        // HandHuman
        GL11.glPushMatrix();
        HandHuman handHuman = new HandHuman();
        float handHumanFacing = 180f;
        GL11.glTranslatef(-400f, 46f, -170f);
        GL11.glRotatef(handHumanFacing, 0f, 1.0f, 0f);
        GL11.glScalef(45f, 45f, 45f);
        handHuman.DrawHuman(delta, coolFaceTexture, tieTexture, pelvisTexture, handHumanFacing);
        GL11.glPopMatrix();

        // LearnHuman
        showLearnHuman(delta,-100f, 46f, -170f);
        showLearnHuman(delta,-700f, 46f, -170f);

        // SleepHuman
        showSleepHuman(delta,-250f, 46f, -370f);
        showSleepHuman(delta,-550f, 46f, -370f);

    }

    public static void main(String[] argv) {
        MainWindow hello = new MainWindow();
        hello.start();
    }

    Texture earthTexture, texture2023, woodEdgeTexture, darkEdgeTexture, lightEdgeTexture;
    Texture back0Texture, back1Texture, back2Texture, back3Texture, back4Texture, back5Texture;
    Texture floorTexture, wallTexture, tableTexture, platformTexture;
    Texture blackboardTexture, clockTexture, soundTexture, keyboardTexture, screenTexture;
    Texture smileFaceTexture, sleepFaceTexture, coolFaceTexture, shineFaceTexture;
    Texture tieTexture, bowTieTexture, pelvisTexture;

    /*
     * Any additional textures for your assignment should be written in here. Make a
     * new texture variable for each one so they can be loaded in at the beginning
     */
    public void init() throws IOException {
        earthTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/earthSpace.png"));
        texture2023 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/2023.png"));

        back0Texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background/back0.png"));
        back1Texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background/back1.png"));
        back2Texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background/back2.png"));
        back3Texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background/back3.png"));
        back4Texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background/back4.png"));
        back5Texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/background/back5.png"));

        floorTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/basic/floor.png"));
        wallTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/wall.png"));
        tableTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/table.png"));
        platformTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/platform.png"));
        blackboardTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/blackboard.png"));
        woodEdgeTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/woodEdge.png"));
        clockTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/clock.png"));
        soundTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/sound.png"));
        screenTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/screen.png"));
        keyboardTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/classroom/keyboard.png"));
        darkEdgeTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/basic/darkEdge.png"));
        lightEdgeTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/basic/lightEdge.png"));

        smileFaceTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/human/smileFace.png"));
        sleepFaceTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/human/sleepFace.png"));
        coolFaceTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/human/coolFace.png"));
        shineFaceTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/human/shineFace.png"));
        tieTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/human/tie.png"));
        bowTieTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/human/bowTie.png"));
        pelvisTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/human/pelvis.png"));

        System.out.println("Texture loaded okay ");
    }
}
