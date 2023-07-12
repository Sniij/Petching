import React, { useState, useEffect, useRef } from 'react';
import io, { Socket } from 'socket.io-client';

type ChatEvent = {
  message: string;
  room: string;
};

const ChatComponent: React.FC = () => {
  const [message, setMessage] = useState<string>('');
  const [messages, setMessages] = useState<{ room: string; message: string }[]>(
    [],
  );
  const socketRef = useRef<Socket | null>(null);
  const [room, setRoom] = useState<string>('defaultRoom');

  useEffect(() => {
    socketRef.current = io('http://localhost:4000');

    socketRef.current.emit('joinRoom', room);

    socketRef.current?.on('chat', (data: ChatEvent) => {
      setMessages(messages => [...messages, data]);
    });

    return () => {
      socketRef.current?.disconnect();
    };
  }, [room]);

  const joinRoom = (e: React.FormEvent) => {
    e.preventDefault();

    if (socketRef.current && room) {
      socketRef.current.emit('joinRoom', room);
    }
  };

  const sendMessage = (e: React.FormEvent) => {
    e.preventDefault();

    if (socketRef.current) {
      socketRef.current.emit('chat', {
        message,
        room,
      });
      setMessage('');
    }
  };

  // Filter messages based on the current room
  const filteredMessages = messages.filter(msg => msg.room === room);

  return (
    <div className="flex flex-col">
      <form onSubmit={joinRoom}>
        <input
          value={room}
          onChange={e => setRoom(e.target.value)}
          className="mb-3 p-2 rounded-md border"
          placeholder="Enter room"
        />
        <button type="submit">Join Room</button>
      </form>
      <div className="overflow-auto mb-4 p-3 flex-grow">
        {filteredMessages.map((message, i) => (
          <p key={i} className="mb-2 text-sm border p-2 rounded-lg bg-gray-100">
            {message.message}
          </p>
        ))}
      </div>
      <form onSubmit={sendMessage} className="flex">
        <input
          id="input"
          type="text"
          value={message}
          onChange={e => setMessage(e.target.value)}
          className="flex-grow border rounded-l-md p-2 focus:outline-none focus:ring-2 focus:ring-green-600"
        />
        <button
          id="send-button"
          type="submit"
          className="bg-green-500 hover:bg-green-600 text-white py-2 px-4 rounded-r-md"
        >
          Send
        </button>
      </form>
    </div>
  );
};

export default ChatComponent;
