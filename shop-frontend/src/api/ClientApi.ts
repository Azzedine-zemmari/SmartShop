import axios from "axios";

const API_URL = "http://localhost:7001"

export const creeClient = (data: any) => {
    return axios
        .post(`${API_URL}/api/v1/client/creeClient`, data)
        .then(res => res.data);
};

export const getClients = () => {
    return axios.get(`${API_URL}/api/v1/client/clients`, { withCredentials: true })
        .then(res => res.data);
}

export const getClientInfo = (id: number) => {
    return axios
        .get(`${API_URL}/api/v1/client/info/${id}`, { withCredentials: true })
        .then(res => res.data);
};
export const deleteClient = (id: number) => {
    return axios.delete(`${API_URL}/api/v1/client/delete/${id}`, {
        withCredentials: true,
    });
};

export const updateClient = (id: number, client: any) => {
    return axios.put(
    `${API_URL}/api/v1/client/update/${id}`,
    client,
    { withCredentials: true }
  ).then(res => res.data);
};
