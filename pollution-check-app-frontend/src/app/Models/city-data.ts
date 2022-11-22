export class CityData {
  constructor(
    public userEmail: string,
    public city: string,
    public state: string,
    public country: string,
    public aqiUS: number,
    public healthStatus: string,
    public id?: number,
  ) {}
}
