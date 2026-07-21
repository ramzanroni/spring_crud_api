# Docker — Full Process Guide (Concepts to Deployment)

## 1. Main Concepts

Docker revolves around two core ideas: **Images** and **Containers**.

### Images

- A read-only, ready-to-use template for running software.
- Built from source code, libraries, external dependencies, and tools.
- Images are immutable — they cannot be changed after creation. To make changes, you build a **new** image.
- Images cannot run directly; they are only templates.
- Think of an image as a snapshot/template of your project.

**What goes into an image:**
- Application code and dependencies
- Runtime/language setup (e.g., Node.js, Java, Python)
- Supporting tools and libraries
- The Dockerfile that defines all of the above

### Containers

- A **runnable instance** of an image.
- Where the image is the blueprint, the container is the live, running thing built from it.

### Base Image (Parent Image)

- The starting template for your project.
- Often, a project depends on a base image for its language/runtime (e.g., a Node.js project needs a Node.js base image; a Java project needs a JDK/JRE base image).

### The Core Workflow

```
Dockerfile → (docker build) → Docker Image → (docker run) → Docker Container
```

---

## 2. Pulling an Image and Running a Container Manually

Before writing your own Dockerfile, you can experiment by pulling an existing image and working inside a container interactively.

**Steps:**
1. Download (pull) an image — either via terminal or Docker Desktop.
   - Example: pull the Node.js image from Docker Hub.
2. Create a container from that image.
3. Run the container.
4. Write and test code directly inside the running container.

**Command:**
```bash
docker run -it node /bin/bash
```
- `-it` = interactive terminal session
- `node` = the image to use
- `/bin/bash` = opens a shell inside the container so you can work inside it directly

*(This can also be done manually through Docker Desktop's UI instead of the terminal.)*

---

## 3. Build a Simple Node.js + Express App (to Containerize Later)

1. Initialize the project:
   ```bash
   npm init -y
   ```
2. Install Express:
   ```bash
   npm install express
   ```
3. Create a simple route using Express in your app's entry file (e.g., `index.js`).
4. Run and test the project locally first:
   ```bash
   node index.js
   ```

Once this works outside Docker, you're ready to containerize it.

---

## 4. Create the Dockerfile and Build Your Own Image

**Steps:**
1. Create a file named `Dockerfile` (no extension) in your project root.
   ```bash
   touch dockerfile
   ```
2. (Optional but helpful) Install the **Docker extension** in VS Code for syntax highlighting and inline commands.
3. Write the Docker instructions inside the Dockerfile (base image, working directory, copy files, install dependencies, expose port, run command).
4. Build the image from the Dockerfile:
   ```bash
   docker build -t basic-app .
   ```
   - `-t basic-app` = tags/names the image `basic-app`
   - `.` = build context is the current directory (where the Dockerfile lives)
5. Verify the image was created:
   - Check Docker Desktop's **Images** tab, or
   - Run:
     ```bash
     docker images
     ```

---

## 5. Run a Container From Your Own Image

**Steps:**
1. Run a container using your built image.
2. Assign a port so you can access the app from your browser/host machine.
   - You can assign a **specific port**, a **random port**, or **no port** (if the container doesn't need external access).
3. Test the running app by visiting the mapped port in a browser or via a request tool (e.g., Postman, curl).

Example:
```bash
docker run -p 5500:5500 basic-app
```
- `-p 5500:5500` = maps host port 5500 to container port 5500

---

## 6. Development with Auto-Reload (Nodemon) + Volumes

For active development, you don't want to rebuild the image every time you change code. **Docker volumes** solve this by mounting your local project folder directly into the container, so changes reflect immediately (combined with `nodemon` for auto-restart).

**Steps:**
1. Install `nodemon` in your project so the app restarts automatically on file changes.
2. Run the container with a **volume mount** linking your local project folder to the container's app folder:
   ```bash
   docker run --name basic-container -p 5500:5500 --rm -v /Users/ramzan/Desktop/basic-app:/app basic-app
   ```
   - `--name basic-container` = names the container for easy reference
   - `-p 5500:5500` = port mapping
   - `--rm` = automatically removes the container when it stops (keeps things clean)
   - `-v /local/path:/app` = mounts your local folder into `/app` inside the container, so edits on your machine are reflected live inside the container

---

## 7. Docker Compose

Instead of running multiple `docker run` commands manually (e.g., one for your app, one for a database), **Docker Compose** lets you define all services, ports, volumes, and networks in a single `docker-compose.yml` file and start everything together with one command:

```bash
docker compose up --build
```

This is especially useful once your project has multiple interconnected pieces (e.g., an app + a database container).

---

## 8. Docker Hub — Push and Pull

To share your image or deploy it elsewhere, you push it to a registry like **Docker Hub**.

**Important convention:** name your build the same as your Docker Hub repository (`<dockerhub-username>/<repo-name>`).

**Build with the correct name:**
```bash
docker build -t ramzanroni/basic-app .
```

**Push it to Docker Hub** (after logging in with `docker login`):
```bash
docker push ramzanroni/basic-app
```

**Pull it elsewhere** (any machine with Docker installed):
```bash
docker pull ramzanroni/basic-app
```

---

## 9. Build for a Specific Platform (Deployment)

When deploying to a server that may use a different CPU architecture than your development machine (e.g., building on an Apple Silicon Mac but deploying to a typical Linux amd64 server), use **buildx** to target that platform explicitly:

```bash
docker buildx build --platform linux/amd64 -t ramzanroni/node-demo-app .
```
- `buildx` = Docker's extended build tool, supports building for specific platforms
- `--platform` = specifies the target OS/architecture (e.g., `linux/amd64`, `linux/arm64`)

---

## 10. Test Deployment on a Temporary Server (Play with Docker)

**Play with Docker** gives you a free, temporary Docker environment in the browser — useful for testing deployment without needing your own server.

**Site:** https://labs.play-with-docker.com/

**Steps once inside the environment:**

1. Check the Docker version installed:
   ```bash
   docker version
   ```
2. Pull your image from Docker Hub:
   ```bash
   docker pull ramzanroni/node-demo-app
   ```
3. Confirm the image downloaded successfully:
   ```bash
   docker images
   ```
4. Run the container, mapping the port so you can access it:
   ```bash
   docker run -p 5500:5500 ramzanroni/node-demo-app
   ```
5. Access the running app through the temporary URL/port that Play with Docker exposes.

---

## Full Lifecycle Summary

```
Write code  →  Write Dockerfile  →  docker build  →  Image created
     →  docker run  →  Container running  →  Test app
     →  docker build -t user/repo .  →  docker push  →  Image on Docker Hub
     →  (on any server) docker pull  →  docker run  →  App deployed
```
