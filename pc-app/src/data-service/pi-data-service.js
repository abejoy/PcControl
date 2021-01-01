import axios from 'axios'

url = 'http://www.abrahamjoys.com:8080';

export const press = () => {
    return axios.get(this.url + '/press')
}

export const turnOff = () => {
    return axios.get(this.url + '/sleepPc')
}

export async const getSleepStatus = () =>  {
    const res = await axios.get(this.url + '/get-sleep-status');
    return !(res.data === 'False');
}
