import React, { useState } from "react";
import { creeClient } from "../ClientApi";

const ClientForm = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [role, setRole] = useState("CLIENT");
    const [nom, setNom] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const client = await creeClient({ username, email, password, role, nom });
            alert(`Client ${client.nom} created!`);
        } catch (error) {
            alert("Error creating client");
            console.error(error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <input placeholder="Username" value={username} onChange={e => setUsername(e.target.value)} />
            <input placeholder="Email" value={email} onChange={e => setEmail(e.target.value)} />
            <input type="password" placeholder="Password" value={password} onChange={e => setPassword(e.target.value)} />
            <input placeholder="Nom" value={nom} onChange={e => setNom(e.target.value)} />
            <button type="submit">Create Client</button>
        </form>
    );
};

export default ClientForm;
