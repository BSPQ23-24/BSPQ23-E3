import express from 'express';
import http from 'http';
import { Server as socketIo } from 'socket.io';
import { fileURLToPath } from 'url';
import path from 'path';

const __dirname = path.dirname(fileURLToPath(import.meta.url));

const app = express();
const server = http.createServer(app);
const io = new socketIo(server);

app.use(express.static(path.join(__dirname, 'src')));

// Maneja la solicitud GET en la raíz del servidor
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'src', 'Chat.html'));
});

io.on('connection', (socket) => {
    console.log('a user connected', socket.id);

    socket.on('chat message', (data) => {
        io.emit('chat message', data);
    });

    socket.on('disconnect', () => {
        console.log('user disconnected', socket.id);
    });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});

