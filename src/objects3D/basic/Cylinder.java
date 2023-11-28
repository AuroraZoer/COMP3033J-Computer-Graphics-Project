package objects3D.basic;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;
import org.lwjgl.opengl.GL11;

public class Cylinder {


    public Cylinder() {
    }

    // remember to use Math.PI isntead PI
    // Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8

    /**
     * Draws a cylinder with the specified radius, height, and number of segments.
     *
     * @param radius    The radius of the cylinder.
     * @param height    The height of the cylinder.
     * @param nSegments The number of segments used to approximate the cylinder's surface.
     */
    public void DrawCylinder(float radius, float height, int nSegments) {

        // Iterate through the segments to create the cylinder's geometry
        for (float i = 0.0f; i < nSegments; i += 1.0) {
            // Calculate angles for the current and next segments
            float angle = (float) (Math.PI * i * 2.0 / nSegments);
            float nextAngle = (float) (Math.PI * (i + 1.0) * 2.0 / nSegments);

            // Calculate trigonometric values for the angles
            float sinForAngle = (float) Math.sin(angle);
            float cosForAngle = (float) Math.cos(angle);
            float sinForNextAngle = (float) Math.sin(nextAngle);
            float cosForNextAngle = (float) Math.cos(nextAngle);

            // Calculate vertex coordinates for the current and next points
            float x1 = sinForAngle * radius;
            float y1 = cosForAngle * radius;
            float x2 = sinForNextAngle * radius;
            float y2 = cosForNextAngle * radius;

            // Define vertices for the two triangles that form the side of the cylinder
            Point4f vertices[] = {
                    new Point4f(x1, y1, 0.0f, 0.0f),     // Vertex 1 of the top triangle
                    new Point4f(x1, y1, height, 0.0f),  // Vertex 2 of the top triangle
                    new Point4f(x2, y2, 0.0f, 0.0f),     // Vertex 1 of the bottom triangle
                    new Point4f(x2, y2, height, 0.0f)   // Vertex 2 of the bottom triangle
            };

            // Calculate normal vectors for the two triangles
            Vector4f v1 = vertices[0].MinusPoint(vertices[3]);
            Vector4f w1 = vertices[0].MinusPoint(vertices[1]);
            Vector4f v2 = vertices[3].MinusPoint(vertices[0]);
            Vector4f w2 = vertices[3].MinusPoint(vertices[2]);
            Vector4f normal1 = v1.cross(w1).Normal(); // Normal vector for the top triangle
            Vector4f normal2 = v2.cross(w2).Normal(); // Normal vector for the bottom triangle

            // Begin drawing the triangles
            GL11.glBegin(GL11.GL_TRIANGLES);

            // Set normal vectors for the triangles
            GL11.glNormal3f(normal1.x, normal1.y, normal1.z);
            GL11.glNormal3f(normal2.x, normal2.y, normal2.z);

            // Draw the top (green) triangle
            GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);
            GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);
            GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);

            // Draw the bottom (red) triangle
            GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);
            GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);
            GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);

            // End drawing the triangles
            GL11.glEnd();

            // Begin drawing the round top and bottom surfaces
            GL11.glBegin(GL11.GL_TRIANGLES);

            // Set normal vectors for the top and bottom surfaces
            GL11.glNormal3f(0.0f, 0.0f, height);
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);

            // Draw the top surface vertices
            GL11.glVertex3f(vertices[1].x, vertices[1].y, vertices[1].z);
            GL11.glVertex3f(vertices[3].x, vertices[3].y, vertices[3].z);
            GL11.glVertex3f(0.0f, 0.0f, height);

            // Draw the bottom surface vertices
            GL11.glVertex3f(vertices[0].x, vertices[0].y, vertices[0].z);
            GL11.glVertex3f(vertices[2].x, vertices[2].y, vertices[2].z);
            GL11.glVertex3f(0.0f, 0.0f, 0.0f);

            // End drawing the cylinder
            GL11.glEnd();
        }
    }
}
