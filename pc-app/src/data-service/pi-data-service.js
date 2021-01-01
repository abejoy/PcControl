import axios from 'axios'

const url = 'http://www.abrahamjoys.com:8080';

export const press = () => {
    console.log('i get herer')
    return axios.get(url + '/press')
}

export const turnOff = () => {
    return axios.get(url + '/sleepPc')
}

export const getSleepStatus = async () =>  {
    const res = await axios.get(url + '/get-sleep-status');
    return !(res.data === 'False');
}

export const submitForm = async (formdata) => {
    return axios.post(url + '/contact-form', formdata);
}
