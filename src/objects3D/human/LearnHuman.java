package objects3D.human;

import GraphicsObjects.Utils;
import objects3D.basic.Cylinder;
import objects3D.basic.Sphere;
import objects3D.basic.TexSphere;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class LearnHuman {
    static float[] white = {1.0f, 1.0f, 1.0f, 1.0f};
    static float[] black = {0.0f, 0.0f, 0.0f, 1.0f};
    static float[] grey = {0.5f, 0.5f, 0.5f, 1.0f};
    static float[] spot = {0.1f, 0.1f, 0.1f, 0.5f};

    public LearnHuman() {
    }

    // Implement using notes in Animation lecture
    public void DrawHuman(float delta, Texture faceTexture, Texture chestTexture, Texture pelvisTexture, float facing) {
        // Calculate the angle of rotation based on the animation delta
        float speed = 20f;  // Controls the speed of the animation
        float theta = (float) (delta * 2 * Math.PI * speed);
        float LimbRotation = (float) Math.cos(theta) * 45;
        float LimbRotation2 = (float) Math.sin(theta) * 45;

        Sphere sphere = new Sphere();
        TexSphere texSphere = new TexSphere();
        Cylinder cylinder = new Cylinder();

        // Shadow
        GL11.glColor4f(spot[0], spot[1], spot[2], spot[3]);
        GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(spot));
        GL11.glPushMatrix();
        {
            GL11.glTranslatef((float) (-0.8f * Math.cos(facing * Math.PI / 180)), -1.0f, (float) (-0.8f * Math.sin(facing * Math.PI / 180)));
            GL11.glRotatef(90f, 1f, 0, 0);
            GL11.glScalef(1f, 1.5f, 1f);
            cylinder.DrawCylinder(1f, 0.01f, 20);
        }
        GL11.glPopMatrix();

        // Pelvis
        GL11.glColor3f(grey[0], grey[1], grey[2]);  // Pelvis colour
        GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
        GL11.glPushMatrix();
        {
            GL11.glTranslatef(0.0f, 0.5f, 0.0f);
            Color.white.bind();
            pelvisTexture.bind();
            GL11.glEnable(GL_TEXTURE_2D);
            GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
            texSphere.DrawTexSphere(0.5f, 32, 32, pelvisTexture);
            GL11.glDisable(GL_TEXTURE_2D);

            // Chest
            GL11.glPushMatrix();
            {
                GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                chestTexture.bind();
                GL11.glEnable(GL_TEXTURE_2D);
                GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                texSphere.DrawTexSphere(0.5f, 32, 32, chestTexture);
                GL11.glDisable(GL_TEXTURE_2D);

                // Neck
                GL11.glColor3f(white[0], white[1], white[2]);  // Neck colour
                GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                    cylinder.DrawCylinder(0.15f, 0.8f, 32);

                    // Head
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 1.0f);
                        GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
                        Color.white.bind();
                        faceTexture.bind();
                        GL11.glEnable(GL_TEXTURE_2D);
                        texSphere.DrawTexSphere(0.5f, 32, 32, faceTexture);
                        GL11.glDisable(GL_TEXTURE_2D);
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();

                    // Right Shoulder
                    GL11.glColor3f(white[0], white[1], white[2]);  // Right Shoulder colour
                    GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.5f, 0.4f, 0.0f);
                        sphere.DrawSphere(0.25f, 32f, 32f);

                        // Right Arm
                        GL11.glColor3f(grey[0], grey[1], grey[2]);  // Right Arm colour
                        GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            GL11.glRotatef(150.0f, 1.0f, 0.0f, 0.0f);
                            cylinder.DrawCylinder(0.15f, 0.7f, 32);

                            // Right Elbow
                            GL11.glColor3f(black[0], black[1], black[2]);  // Right Elbow colour
                            GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(black));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                sphere.DrawSphere(0.2f, 32f, 32f);

                                // Right Forearm
                                GL11.glColor3f(grey[0], grey[1], grey[2]);  // Right Forearm colour
                                GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(LimbRotation / 10, 1.0f, 0f, 0f);
                                    GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
                                    cylinder.DrawCylinder(0.1f, 0.6f, 32);

                                    // Right Hand
                                    GL11.glColor3f(white[0], white[1], white[2]);  // Right Hand colour
                                    GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
                                    GL11.glPushMatrix();
                                    {
                                        GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                        sphere.DrawSphere(0.2f, 32f, 32f);
                                    }
                                    GL11.glPopMatrix();
                                }
                                GL11.glPopMatrix();
                            }
                            GL11.glPopMatrix();
                        }
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();

                    // Left Shoulder
                    GL11.glColor3f(white[0], white[1], white[2]);  // Left Shoulder colour
                    GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(-0.5f, 0.4f, 0.0f);
                        sphere.DrawSphere(0.25f, 32f, 32f);

                        // Left Arm
                        GL11.glColor3f(grey[0], grey[1], grey[2]);  // Left Arm colour
                        GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                            GL11.glRotatef(150.0f, 1.0f, 0.0f, 0.0f);
                            cylinder.DrawCylinder(0.15f, 0.7f, 32);

                            // Left Elbow
                            GL11.glColor3f(black[0], black[1], black[2]);  // Left Elbow colour
                            GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(black));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                sphere.DrawSphere(0.2f, 32f, 32f);

                                // Left Forearm
                                GL11.glColor3f(grey[0], grey[1], grey[2]);  // Left Forearm colour
                                GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(15.0f, 1.0f, 0.0f, 0.0f);
                                    GL11.glRotatef(LimbRotation2 / 10, 1.0f, 0f, 0f);
                                    GL11.glRotatef(20.0f, 0.0f, 1.0f, 0.0f);
                                    cylinder.DrawCylinder(0.1f, 0.6f, 32);

                                    // Left Hand
                                    GL11.glColor3f(white[0], white[1], white[2]);  // Left Hand colour
                                    GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
                                    GL11.glPushMatrix();
                                    {
                                        GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                        sphere.DrawSphere(0.2f, 32f, 32f);
                                    }
                                    GL11.glPopMatrix();
                                }
                                GL11.glPopMatrix();
                            }
                            GL11.glPopMatrix();
                        }
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();

                // Pelvis 下面连接的两条腿
                // Right Hip
                GL11.glColor3f(white[0], white[1], white[2]);  // Right Hip colour
                GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(0.5f, -0.4f, 0.0f);
                    sphere.DrawSphere(0.25f, 32f, 32f);

                    // Right Thigh
                    GL11.glColor3f(grey[0], grey[1], grey[2]);  // Right Thigh colour
                    GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(165.0f, 1.0f, 0.0f, 0.0f);
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);

                        // Right Knee
                        GL11.glColor3f(grey[0], grey[1], grey[2]);  // Right Knee colour
                        GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                            sphere.DrawSphere(0.2f, 32f, 32f);

                            // Right shin
                            GL11.glColor3f(grey[0], grey[1], grey[2]);  // Right shin colour
                            GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                                cylinder.DrawCylinder(0.1f, 0.6f, 32);

                                // Right foot
                                GL11.glColor3f(black[0], black[1], black[2]);  // Right foot colour
                                GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(black));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                    sphere.DrawSphere(0.3f, 32, 32);
                                }
                                GL11.glPopMatrix();
                            }
                            GL11.glPopMatrix();
                        }
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();

                // Left Hip
                GL11.glColor3f(white[0], white[1], white[2]);  // Left Hip colour
                GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
                GL11.glPushMatrix();
                {
                    GL11.glTranslatef(-0.5f, -0.4f, 0.0f);
                    sphere.DrawSphere(0.25f, 32f, 32f);

                    // Left Thigh
                    GL11.glColor3f(grey[0], grey[1], grey[2]);  // Left Thigh colour
                    GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                    GL11.glPushMatrix();
                    {
                        GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                        GL11.glRotatef(165.0f, 1.0f, 0.0f, 0.0f);
                        cylinder.DrawCylinder(0.15f, 0.7f, 32);

                        // Left Knee
                        GL11.glColor3f(grey[0], grey[1], grey[2]);  // Left Knee colour
                        GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                        GL11.glPushMatrix();
                        {
                            GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                            sphere.DrawSphere(0.2f, 32f, 32f);

                            // Left shin
                            GL11.glColor3f(grey[0], grey[1], grey[2]);  // Left shin colour
                            GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(grey));
                            GL11.glPushMatrix();
                            {
                                GL11.glTranslatef(0.0f, 0.0f, 0.0f);
                                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                                cylinder.DrawCylinder(0.1f, 0.6f, 32);

                                // Left foot
                                GL11.glColor3f(black[0], black[1], black[2]);  // Left foot colour
                                GL11.glMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(black));
                                GL11.glPushMatrix();
                                {
                                    GL11.glTranslatef(0.0f, 0.0f, 0.75f);
                                    sphere.DrawSphere(0.3f, 32, 32);
                                }
                                GL11.glPopMatrix();
                            }
                            GL11.glPopMatrix();
                        }
                        GL11.glPopMatrix();
                    }
                    GL11.glPopMatrix();
                }
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        // End of Human
    }
}
