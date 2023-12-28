package objects3D.human;

import GraphicsObjects.Utils;
import objects3D.basic.Cylinder;
import objects3D.basic.Sphere;
import objects3D.basic.TexSphere;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class Dog {
    static float[] brown = {0.5f, 0.25f, 0.0f, 1.0f, 1.0f};
    static float[] grey = {0.5f, 0.5f, 0.5f, 1.0f};
    static float[] black = {0.0f, 0.0f, 0.0f, 1.0f};

    public Dog() {
    }

    public void DrawDog(float delta, boolean animation, Texture faceTexture, Texture pelvisTexture, float facing) {
        // Calculate the angle of rotation based on the animation delta
        float speed = 5f;  // Controls the speed of the animation
        float theta = (float) (delta * 10 * Math.PI * speed);
        float LimbRotation;
        if (animation) {
            LimbRotation = (float) Math.cos(theta) * 45;
        } else {
            LimbRotation = 0;
        }

        Sphere sphere = new Sphere();
        TexSphere texSphere = new TexSphere();
        Cylinder cylinder = new Cylinder();

        // Pelvis
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0.0f, 0.5f, 0.0f);
            Color.white.bind();
            pelvisTexture.bind();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            texSphere.DrawTexSphere(0.42f, 32, 32, pelvisTexture);
            GL11.glRotatef(-90, 1, 0, 0);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            // tail
            GL11.glPushMatrix();
            {
                GL11.glScalef(0.3f, 1.5f, 0.3f);
                GL11.glTranslatef(0f, -0.3f, 0.55f);
                GL11.glRotatef(60, 1, 0, 0);
                sphere.DrawSphere(0.25f, 32, 32);
            }
            GL11.glPopMatrix();

            //chest
            //configure the pen (color & material)
            GL11.glColor3f(brown[0], brown[1], brown[2]);
            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0.0f, 0.5f, 0.0f);//translate the coordinate to +0.5y
                GL11.glRotatef(90, 1, 0, 0);
                sphere.DrawSphere(0.42f, 32, 32);
                GL11.glRotatef(-90, 1, 0, 0);

                // Neck
                GL11.glColor3f(brown[0], brown[1], brown[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.0f, 0.5f, 0.0f);

                    // Head
                    GL11.glColor3f(brown[0], brown[1], brown[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                    GL11.glPushMatrix();
                    {

                        GL11.glTranslatef(0.0f, 0.0f, 0.5f);//translate the coordinate +1z
                        Color.white.bind();
                        faceTexture.bind();
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
                        texSphere.DrawTexSphere(0.4f, 32, 32, faceTexture);
                        GL11.glDisable(GL11.GL_TEXTURE_2D);

                        GL11.glPopMatrix();//pop matrix -->back to neck
                    }
                    GL11.glPopMatrix();//pop matrix -->back to chest

                    //left shoulder
                    //configure the pen (color & material)
                    GL11.glColor3f(brown[0], brown[1], brown[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.5f, 0.4f, 0.0f);//translate the coordinate (+0.5x,+0.4y)
                        sphere.DrawSphere(0.25f, 32, 32);//draw the sphere standing for the left shoulder

                        // left arm
                        //configure the pen (color & material)
                        GL11.glColor3f(brown[0], brown[1], brown[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);//doesn't translate the coordinate
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);//rotate the coordinate 90 degree along (1,0,0)
                            GL11.glRotatef(LimbRotation + 90, 1.0f, 0.0f, 0.0f);//rotate the coordinate "LimbRotation" degree along (1,0,0)
                            if (LimbRotation > 0) {
                                GL11.glRotatef(-LimbRotation, 0, 0, 1);
                            }
                            cylinder.DrawCylinder(0.15f, 0.75f, 32);//draw the cylinder standing for the left arm


                            // left elbow
                            //configure the pen (color & material)
                            GL11.glColor3f(brown[0], brown[1], brown[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);//translate the coordinate (+0.75z)

                                sphere.DrawSphere(0.2f, 32, 32); //draw the sphere standing for the left elbow

                            }
                            GL11.glPopMatrix();//pop matrix --> back to arm
                        }
                        GL11.glPopMatrix();//pop matrix --> back to shoulder
                    }
                    GL11.glPopMatrix();//pop matrix--> back to chest

                    // right shoulder
                    //configure the pen (color & material)
                    GL11.glColor3f(brown[0], brown[1], brown[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(-0.5f, 0.4f, 0.0f);//translate the matrix along(0.5 0.4 0.0)
                        sphere.DrawSphere(0.25f, 32, 32); //draw the sphere standing for shoulder

                        // right arm
                        //configure the pen (color & material)
                        GL11.glColor3f(brown[0], brown[1], brown[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);//doesn't translate the coordinate system
                            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);//rotate the coordinate system 90 degree along (1,0,0)
                            GL11.glRotatef(-LimbRotation + 90, 1.0f, 0.0f, 0.0f); //rotate the coordinate system 90 degree along (1,0,0)
                            if (LimbRotation < 0) {
                                GL11.glRotatef(-LimbRotation, 0, 0, 1);
                            }
                            cylinder.DrawCylinder(0.15f, 0.7f, 32);//draw the cylinder standing for the right arm

                            // right elbow
                            //configure the pen (color & material)
                            GL11.glColor3f(brown[0], brown[1], brown[2]);
                            GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));

                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);//translate the coordinate system (+0.75z)
                                sphere.DrawSphere(0.2f, 32, 32); //draw the sphere standing for elbow
                            }
                            GL11.glPopMatrix();//pop matrix --> back to right arm
                        }
                        GL11.glPopMatrix();//pop matrix --> back to right shoulder
                    }
                    GL11.glPopMatrix();//pop matrix --> back to chest
                }
                GL11.glPopMatrix();//pop matrix --> back to pelvis

                // left hip
                //configure the pen (color & material)
                GL11.glColor3f(brown[0], brown[1], brown[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(-0.5f, -0.5f, 0.0f);//translate the coordinate system along (-0.5f,-0.2f)
                    sphere.DrawSphere(0.25f, 32, 32); //draw the sphere standing for hip

                    //left high leg
                    //configure the pen (color & material)
                    GL11.glColor3f(brown[0], brown[1], brown[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);//doesn't translate the coordinate system
                        GL11.glRotatef(90, 1, 0.0f, 0.0f);//doesn't rotate the cylinder

                        GL11.glRotatef((-LimbRotation) + 90, 1.0f, 0.0f, 0.0f); //rotate the cylinder to simulate the action of running legs
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);//draw the cylinder standing for high legs

                        // left knee
                        //configure the pen (color & material)
                        GL11.glColor3f(brown[0], brown[1], brown[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);//translate the coordinate along (0.75z)
                            GL11.glRotatef(90, 1, 0.0f, 0.0f); //doesn't rotate the coordinate system
                            sphere.DrawSphere(0.25f, 32, 32); //draw the sphere standing for the knee
                        }
                        GL11.glPopMatrix();//pop matrix --> back to high leg
                    }
                    GL11.glPopMatrix();//pop matrix --> back to left hip
                }
                GL11.glPopMatrix();//pop matrix --> back to pelvis

                //right hip
                //configure the pen (color & material)
                GL11.glColor3f(brown[0], brown[1], brown[2]);
                GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                GL11.glPushMatrix();
                {

                    GL11.glTranslatef(0.5f, -0.5f, 0.0f);//translate the coordinate along (0.5,-0.2,0.0)
                    sphere.DrawSphere(0.25f, 32, 32);//draw the sphere standing for the right hip

                    //right high leg
                    //configure the pen (color & material)
                    GL11.glColor3f(brown[0], brown[1], brown[2]);
                    GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);//doesn't translate the coordinate system
                        GL11.glRotatef(90, 1, 0.0f, 0.0f);//doesn't rotate the coordinate system
                        GL11.glRotatef((LimbRotation) + 90, 1.0f, 0.0f, 0.0f); //rotate the coordinate system (LimbRotation+90) degree along (1.0,0.0,0.0) w
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);//draw the cylinder standing for right high pen

                        //right knee
                        //configure the pen (color & material)
                        GL11.glColor3f(brown[0], brown[1], brown[2]);
                        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(brown));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);//translate the coordinate (0.75z)
                            GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
                            sphere.DrawSphere(0.25f, 32, 32); //draw the sphere standing for knee
                        }
                        GL11.glPopMatrix();//pop matrix --> back to high leg
                    }
                    GL11.glPopMatrix();//pop matrix --> back to hip
                }
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
    }
}
