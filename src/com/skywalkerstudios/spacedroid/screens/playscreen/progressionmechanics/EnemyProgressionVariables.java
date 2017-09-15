package com.skywalkerstudios.spacedroid.screens.playscreen.progressionmechanics;

public class EnemyProgressionVariables {
	
	public static class Asteroid {
		
		private static float velocity;
		private static float health;
		
		// Setter methods
		
		public static void setVelocity(float newVelocity) {
			Asteroid.velocity = newVelocity;
		}

		public static void setHealth(float newHealth) {
			Asteroid.health = newHealth;
		}
		
		// Increase methods
		
		public static void increaseVelocity(float n) {
			Asteroid.velocity += n;
		}
		
		public static void increaseHealth(float n) {
			Asteroid.health += n;
		}
		
		// Getter methods
		
		public static float getVelocity() {
			return Asteroid.velocity;
		}
		
		public static float getHealth() {
			return Asteroid.health;
		}

		
	}
	
	public static class SlowStrongDroid {
		
		private static float velocity;
		private static float health;
		private static float laserVelocity;
		private static float laserDamage;
		private static float laserFrequency;
		
		// Setter methods
		
		public static void setVelocity(float newVelocity) {
			SlowStrongDroid.velocity = newVelocity;
		}

		public static void setHealth(int newHealth) {
			SlowStrongDroid.health = newHealth;
		}

		public static void setLaserVelocity(float newLaserVelocity) {
			SlowStrongDroid.laserVelocity = (newLaserVelocity + SlowStrongDroid.velocity);
		}
		
		public static void setLaserDamage(float newLaserDamage) {
			SlowStrongDroid.laserDamage = newLaserDamage;
		}
		
		public static void setLaserFrequency(float newLaserFrequency) {
			SlowStrongDroid.laserFrequency = newLaserFrequency;
		}
		
		// Increase methods
		
		public static void increaseVelocity(float n) {
			SlowStrongDroid.velocity += n;
		}
		
		public static void increaseHealth(float n) {
			SlowStrongDroid.health += n;
		}
		
		public static void increaseLaserVelocity(float n) {
			SlowStrongDroid.laserVelocity += n;
		}
		
		public static void increaseLaserDamage(float n) {
			SlowStrongDroid.laserDamage += n;
		}
		
		public static void increaseLaserFrequency(float n) {
			SlowStrongDroid.laserFrequency += n;
		}
		
		// Getter methods
		
		public static float getVelocity() {
			return SlowStrongDroid.velocity;
		}
		
		public static float getHealth() {
			return SlowStrongDroid.health;
		}
		
		public static float getLaserVelocity() {
			return SlowStrongDroid.laserVelocity;
		}
		
		public static float getLaserDamage() {
			return SlowStrongDroid.laserDamage;
		}
		
		public static int getLaserFrequency() {
			return (int) (1000/SlowStrongDroid.laserFrequency);
		}
		
		
	}

	public static class FastWeakDroid {
		
		private static float velocity;
		private static float health;
		private static float laserVelocity;
		private static float laserDamage;
		private static float laserFrequency;
		
		// Setter methods
		
		public static void setVelocity(float newVelocity) {
			FastWeakDroid.velocity = newVelocity;
		}

		public static void setHealth(float newHealth) {
			FastWeakDroid.health = newHealth;
		}

		public static void setLaserVelocity(float newLaserVelocity) {
			FastWeakDroid.laserVelocity = (newLaserVelocity + FastWeakDroid.velocity);
		}
		
		public static void setLaserDamage(float newLaserDamage) {
			FastWeakDroid.laserDamage = newLaserDamage;
		}
		
		public static void setLaserFrequency(float newLaserFrequency) {
			FastWeakDroid.laserFrequency = newLaserFrequency;
		}
		
		// Increase methods
		
		public static void increaseVelocity(float n) {
			FastWeakDroid.velocity += n;
		}
		
		public static void increaseHealth(float n) {
			FastWeakDroid.health += n;
		}
		
		public static void increaseLaserVelocity(float n) {
			FastWeakDroid.laserVelocity += n;
		}
		
		public static void increaseLaserDamage(float n) {
			FastWeakDroid.laserDamage += n;
		}
		
		public static void increaseLaserFrequency(float n) {
			FastWeakDroid.laserFrequency += n;
		}
		
		// Getter methods
		
		public static float getVelocity() {
			return FastWeakDroid.velocity;
		}
		
		public static float getHealth() {
			return FastWeakDroid.health;
		}
		
		public static float getLaserVelocity() {
			return FastWeakDroid.laserVelocity;
		}
		
		public static float getLaserDamage() {
			return FastWeakDroid.laserDamage;
		}
		
		public static int getLaserFrequency() {
			return (int) (1000/FastWeakDroid.laserFrequency);
		}
		
		
	}

	public static class Boss {
		
		private static float velocity;
		private static float health;
		private static float laserVelocity;
		private static float laserDamage;
		private static float laserFrequency;
		private static float laserAcceleration;
		
		// Setter methods
		
		public static void setVelocity(float newVelocity) {
			Boss.velocity = newVelocity;
		}

		public static void setHealth(float newHealth) {
			Boss.health = newHealth;
		}

		public static void setLaserVelocity(float newLaserVelocity) {
			Boss.laserVelocity = (newLaserVelocity + Boss.velocity);
		}
		
		public static void setLaserDamage(float newLaserDamage) {
			Boss.laserDamage = newLaserDamage;
		}
		
		public static void setLaserFrequency(float newLaserFrequency) {
			Boss.laserFrequency = newLaserFrequency;
		}
		
		public static void setLaserAcceleration(float newAcceleration) {
			Boss.laserAcceleration = newAcceleration;
			
		}
		
		// Increase methods
		
		public static void increaseVelocity(float n) {
			Boss.velocity += n;
		}
		
		public static void increaseHealth(float n) {
			Boss.health += n;
		}
		
		public static void increaseLaserVelocity(float n) {
			Boss.laserVelocity += n;
		}
		
		public static void increaseLaserDamage(float n) {
			Boss.laserDamage += n;
		}
		
		public static void increaseLaserFrequency(float n) {
			Boss.laserFrequency += n;
		}
		
		// Getter methods
		
		public static float getVelocity() {
			return Boss.velocity;
		}
		
		public static float getHealth() {
			return Boss.health;
		}
		
		public static float getLaserVelocity() {
			return Boss.laserVelocity;
		}
		
		public static float getLaserDamage() {
			return Boss.laserDamage;
		}
		
		public static float getLaserAcceleration() {
			return Boss.laserAcceleration;
			
		}
		
		public static int getLaserFrequency() {
			return (int) (1000/Boss.laserFrequency);
		}
		
		
	}

	
}
