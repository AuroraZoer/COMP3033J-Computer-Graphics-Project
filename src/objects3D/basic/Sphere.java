package objects3D.basic;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_QUADS;

public class Sphere {

    public Sphere() {

    }

    // Implement using notes and examine Tetrahedron to aid in the coding look at
    // lecture 7 , 7b and 8
    // 7b should be your primary source, we will cover more about circles in later
    // lectures to understand why the code works

    /**
     * Draw a sphere with the given radius, number of slices, and number of segments.
     *
     * @param radius    The radius of the sphere.
     * @param nSlices   The number of slices (horizontal).
     * @param nSegments The number of segments (vertical).
     */
    public void DrawSphere(float radius, float nSlices, float nSegments) {
        // Calculate the angular increments for theta and phi.
        float inctheta = (float) ((2.0f * Math.PI) / nSlices);   // Angular increment for horizontal slices.
        float incphi = (float) ((Math.PI) / nSegments);           // Angular increment for vertical stacks.

        // Begin drawing quadrilaterals (GL_QUADS) to approximate the sphere.
        GL11.glBegin(GL_QUADS);

        // Iterate over the horizontal slices (theta) and vertical stacks (phi).
        for (float theta = (float) -(Math.PI); theta < Math.PI; theta += inctheta) {
            for (float phi = (float) -((Math.PI) / 2.0f); phi < ((Math.PI) / 2.0f); phi += incphi) {
                // Calculate the spherical coordinates of the current point.
                float x = radius * (float) (Math.cos(phi) * Math.cos(theta));
                float y = radius * (float) (Math.cos(phi) * Math.sin(theta));
                float z = radius * (float) (Math.sin(phi));

                // Calculate the coordinates of adjacent points on the sphere to form a quadrilateral.
                float x1 = radius * (float) (Math.cos(phi + incphi) * Math.cos(theta));
                float y1 = radius * (float) (Math.cos(phi + incphi) * Math.sin(theta));
                float z1 = radius * (float) (Math.sin(phi + incphi));

                float x2 = radius * (float) (Math.cos(phi + incphi) * Math.cos(theta + inctheta));
                float y2 = radius * (float) (Math.cos(phi + incphi) * Math.sin(theta + inctheta));
                float z2 = radius * (float) (Math.sin(phi + incphi));

                float x3 = radius * (float) (Math.cos(phi) * Math.cos(theta + inctheta));
                float y3 = radius * (float) (Math.cos(phi) * Math.sin(theta + inctheta));
                float z3 = radius * (float) (Math.sin(phi));

                // Set the normal vector of the current point (used for lighting calculations).
                GL11.glNormal3f(x, y, z);

                // Define the vertices of the quadrilateral (a square).
                GL11.glVertex3f(x, y, z);     // Vertex 0
                GL11.glVertex3f(x1, y1, z1);  // Vertex 1
                GL11.glVertex3f(x2, y2, z2);  // Vertex 2
                GL11.glVertex3f(x3, y3, z3);  // Vertex 3
            }
        }

        // End drawing quadrilaterals.
        GL11.glEnd();
    }
}
