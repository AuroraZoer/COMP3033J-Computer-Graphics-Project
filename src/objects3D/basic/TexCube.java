package objects3D.basic;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

import static org.lwjgl.opengl.GL11.*;

public class TexCube {

    public TexCube() {

    }

    // Implement using notes  and looking at TexSphere

    /**
     * Draw a textured cube using the provided texture.
     */
    public void DrawTexCube() {
// Define the eight vertices of the cube
        Point4f vertices[] = {
                new Point4f(-1.0f, -1.0f, -1.0f, 0.0f),
                new Point4f(-1.0f, -1.0f, 1.0f, 0.0f),
                new Point4f(-1.0f, 1.0f, -1.0f, 0.0f),
                new Point4f(-1.0f, 1.0f, 1.0f, 0.0f),
                new Point4f(1.0f, -1.0f, -1.0f, 0.0f),
                new Point4f(1.0f, -1.0f, 1.0f, 0.0f),
                new Point4f(1.0f, 1.0f, -1.0f, 0.0f),
                new Point4f(1.0f, 1.0f, 1.0f, 0.0f)
        };

        // Define the faces of the cube in terms of vertex indices
        int faces[][] = {
                {0, 4, 5, 1},
                {0, 2, 6, 4},
                {0, 1, 3, 2},
                {4, 6, 7, 5},
                {1, 5, 7, 3},
                {2, 3, 7, 6}
        };

        glBegin(GL_QUADS);

        for (int face = 0; face < 6; face++) { // Iterate over each face
            // Calculate the normal vector for the current face
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][3]].MinusPoint(vertices[faces[face][0]]);
            Vector4f normal = v.cross(w).Normal();
            glNormal3f(normal.x, normal.y, normal.z);

            // Set texture coordinates for each vertex of the face
            glTexCoord2d(0, 0);
            glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);
            glTexCoord2d(1, 0);
            glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);
            glTexCoord2d(1, 1);
            glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);
            glTexCoord2d(0, 1);
            glVertex3f(vertices[faces[face][3]].x, vertices[faces[face][3]].y, vertices[faces[face][3]].z);
        } // End of per face iteration

        glEnd();
    }


}