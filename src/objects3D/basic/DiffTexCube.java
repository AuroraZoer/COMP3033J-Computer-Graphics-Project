package objects3D.basic;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class DiffTexCube {

    public DiffTexCube() {

    }

    // Implement using notes and looking at TexSphere
    public void drawTexCube(Texture[] textures) {

        Point4f vertices[] = {
                // Set the coordinates of the vertices of the cube.
                new Point4f(1.0f, 1.0f, 1.0f, 0.0f), //0
                new Point4f(-1.0f, 1.0f, 1.0f, 0.0f), //1
                new Point4f(-1.0f, 1.0f, -1.0f, 0.0f), //2
                new Point4f(1.0f, 1.0f, -1.0f, 0.0f), //3
                new Point4f(-1.0f, -1.0f, 1.0f, 0.0f), //4
                new Point4f(-1.0f, -1.0f, -1.0f, 0.0f), //5
                new Point4f(1.0f, -1.0f, -1.0f, 0.0f), //6
                new Point4f(1.0f, -1.0f, 1.0f, 0.0f) //7
        };

        int[][] faces = {
                // Each subarray contains three indices of the vertex
                // Four vertices can define one rectangle face
                {7, 4, 5, 6}, //0
                {2, 3, 6, 5}, //1
                {1, 2, 5, 4}, //2
                {3, 0, 7, 6}, //3
                {0, 1, 4, 7}, //4
                {3, 2, 1, 0}, //5
        };

        // Start to draw the faces

        for (int face = 0; face < 6; face = face + 1) { // per face
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);

            Color.white.bind();
            textures[face].bind();
            glEnable(GL_TEXTURE_2D);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GL11.glBegin(GL_QUADS);
            // Calculate the normal vector of the face to decide the shading
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][3]].MinusPoint(vertices[faces[face][0]]);
            Vector4f normal = v.cross(w).Normal();

            // Build the normal vector in OpenGL
            GL11.glNormal3f(normal.x, normal.y, normal.z);

            // Build the vertices of the face in OpenGL and set the corresponding texture coordinate
            glTexCoord2f(0, 0);
            GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);
            glTexCoord2f(1, 0);
            GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);
            glTexCoord2f(1, 1);
            GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);
            glTexCoord2f(0, 1);
            GL11.glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);
            GL11.glEnd();
        } // per face
        // Ending of drawing

    }

}

/*
 *
 *
 * }
 *
 */
