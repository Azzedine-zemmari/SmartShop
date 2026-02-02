import axios from "axios";

const API_URL = "http://localhost:8080"

export const creeClient = (data: any) => {
    return axios
    .post(`${API_URL}/api/v1/client/creeClient`, data)
    .then(res => res.data);
};

export const getClients = () => {
    return axios.get(`${API_URL}/api/v1/client/clients`, { withCredentials: true })
    .then(res => res.data);
}