# COMP3033J-Computer-Graphics-Project

- Yiran Zhao
- UCD Number: 21207295

- Click [here](https://youtu.be/X-0f-thmvQM) to watch the project1 video.
- Click [here](https://youtu.be/jkHe-UU_CKA) to watch the project2 video.

## Complexity of the Scene

This interactive classroom scene is rich in elements and details. It includes:

- **Main Characters & Models**: Detailed models of main characters, a following dog, sleeping students, students raising
  hands, and a student typing.
- **Dynamic Teacher**: An animated teacher character walking back and forth on the podium.
- **Welcoming Character**: A character jumping joyously at the entrance of the classroom.
- **Rotating Globe**: An automatically rotating globe that stops when the main character approaches.
- **Classroom Essentials**: Blackboard, sound system, tables, chairs, podium, and wall paintings.

## Detail of Models & Usage of Scene Graph

Each character and the dog have been meticulously implemented with a logical structure and realistic animations. The
scene graph is used effectively to manage these complex interactions.

## Lighting, Textures & Shadows

The scene utilizes various lighting techniques, detailed textures, and realistic shadows to enhance the visual
experience.

## Adjustment of the Camera

Different camera angles are employed, including first-person and third-person perspectives of the main character. There
is an additional camera mode that moves according to the teacher's position.

## Kinematics & Animation

Multiple objects move logically within the scene, influenced by basic principles of kinematics and a simple physics
engine.

## Interaction

- **Character Movement**: Pressing the 'W', 'A', 'S', 'D' keys moves the main character in respective directions. The
  camera view and the following dog's movements align with these directions.
- **Jumping**: Pressing the 'space' key makes the character jump.
- **Globe Interaction**: The rotating globe stops when the main character approaches it.
- **Waking Up Sleeping Students**: Pressing the 'X' key wakes up a sleeping student if the main character is nearby. The
  key has no effect if the character is far away.
- **Teacher's Camera Mode**: Pressing the 'Z' key switches to a camera that follows the teacher; pressing it again
  returns to the previous mode.
- **Switching Camera Views**: Press the 'F' key to toggle between first-person and third-person views of the main
  character.
- **Camera Rotation Control**: Holding down the left mouse button allows for control of the camera's rotation angle.
- **Resetting Camera View**: Pressing the 'R' key resets the camera to its initial perspective.
