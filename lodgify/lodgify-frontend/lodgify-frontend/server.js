import express from 'express';
import http from 'http';
import { Server as socketIo } from 'socket.io';
import { fileURLToPath } from 'url';
import path from 'path';
import cors from 'cors';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

const app = express();
const server = http.createServer(app);
const io = new socketIo(server, {
  cors: {
    origin: 'http://localhost:8080',
    methods: ['GET', 'POST'],
  }
});

app.use(cors({
  origin: 'http://localhost:8080',
}));

app.use(express.static(path.join(__dirname, 'src')));

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'src', 'Chat.html'));
});

const users = {};

io.on('connection', (socket) => {
    console.log('a user connected', socket.id);

    socket.on('register', (username) => {
        users[username] = socket.id;
        console.log(`User registered: ${username} with socket ID: ${socket.id}`);
    });

    socket.on('private message', ({ message, to }) => {
        const recipientSocketId = users[to];
        if (recipientSocketId) {
            console.log(`Sending message: "${message}" to user: ${to}`);
            io.to(recipientSocketId).emit('chat message', { message, socketId: socket.id });
        } else {
            console.log(`User: ${to} not found`);
        }
    });

    socket.on('disconnect', () => {
        console.log('user disconnected', socket.id);
        for (let username in users) {
            if (users[username] === socket.id) {
                delete users[username];
                break;
            }
        }
    });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
