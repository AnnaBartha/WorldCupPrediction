terraform {
  required_providers {
    google = {
      source  = "hashicorp/google"
      version = "~> 6.0"
    }
  }
}

provider "google" {
  project = "demoproject002-447614"
  region  = "europe-west1"
  zone    = "europe-west1-b"
}

resource "google_compute_firewall" "grafana_prometheus" {
  name    = "allow-grafana-prometheus-tf"
  network = "default"

  direction = "INGRESS"

  allow {
    protocol = "tcp"
    ports    = ["30300", "30090"]
  }

  source_ranges = ["0.0.0.0/0"]

  description = "Allow external access to Grafana and Prometheus NodePorts for demo."
}
