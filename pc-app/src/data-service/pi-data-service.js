import axios from 'axios'

export class PiDataService {
    url = 'http://www.abrahamjoys.com'

    //url = '/'

    press() {
        return axios.get(this.url + '/press')
    }

    turnOff() {
        return axios.get(this.url + '/sleepPc')
    }

    async getSleepStatus() {
        const res = await axios.get(this.url + '/get-sleep-status');
        return !(res.data === 'False');
    }
}