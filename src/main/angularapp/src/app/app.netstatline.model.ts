export interface NetstatLine {
  timestamp: string;
  proto: string;
  localAddress: string;
  localPort: number;
  localAddressIPVersion: string;
  foreignAddress: string;
  foreignPort: number;
  foreignAddressIPVersion: string;
  state: string;
  pid: number;
}
